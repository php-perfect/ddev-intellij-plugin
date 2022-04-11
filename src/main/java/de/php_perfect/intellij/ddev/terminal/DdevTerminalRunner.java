package de.php_perfect.intellij.ddev.terminal;

import com.intellij.execution.TaskExecutor;
import com.intellij.execution.configurations.PtyCommandLine;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessWaitFor;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.terminal.JBTerminalWidget;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.jediterm.pty.PtyProcessTtyConnector;
import com.jediterm.terminal.TtyConnector;
import com.pty4j.PtyProcess;
import com.pty4j.unix.UnixPtyProcess;
import de.php_perfect.intellij.ddev.cmd.wsl.WslAware;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.plugins.terminal.AbstractTerminalRunner;
import org.jetbrains.plugins.terminal.TerminalProcessOptions;
import org.jetbrains.plugins.terminal.TerminalProjectOptionsProvider;

import java.awt.*;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public final class DdevTerminalRunner extends AbstractTerminalRunner<PtyProcess> {
    private static final Logger LOG = Logger.getInstance(DdevTerminalRunner.class);

    public DdevTerminalRunner(@NotNull Project project) {
        super(project);
    }

    @Override
    public @NotNull PtyProcess createProcess(@NotNull TerminalProcessOptions options, @Nullable JBTerminalWidget widget) throws ExecutionException {
        State ddevState = DdevStateManager.getInstance(this.myProject).getState();

        if (!ddevState.isInstalled()) {
            throw new ExecutionException("DDEV not installed", null);
        }

        final PtyCommandLine commandLine = new PtyCommandLine(List.of("ddev", "ssh"))
                .withConsoleMode(false);

        commandLine.setWorkDirectory(getProject().getBasePath());

        final PtyCommandLine patchedCommandLine = WslAware.patchCommandLine(commandLine);

        try {
            return (PtyProcess) patchedCommandLine.createProcess();
        } catch (com.intellij.execution.ExecutionException e) {
            throw new ExecutionException("Opening DDEV Terminal failed", e);
        }
    }

    @Override
    protected ProcessHandler createProcessHandler(final PtyProcess process) {
        return new DdevTerminalRunner.PtyProcessHandler(process, getShellPath());
    }

    @Override
    protected @NotNull TtyConnector createTtyConnector(@NotNull PtyProcess process) {
        return new PtyProcessTtyConnector(process, StandardCharsets.UTF_8) {

            @Override
            public void close() {
                if (process instanceof UnixPtyProcess) {
                    ((UnixPtyProcess) process).hangup();
                    AppExecutorUtil.getAppScheduledExecutorService().schedule(() -> {
                        if (process.isAlive()) {
                            LOG.info("Terminal hasn't been terminated by SIGHUP, performing default termination");
                            process.destroy();
                        }
                    }, 1000, TimeUnit.MILLISECONDS);
                } else {
                    process.destroy();
                }
            }

            @Override
            public void resize(@NotNull Dimension termWinSize) {
                if (LOG.isDebugEnabled()) {
                    LOG.debug("resize to " + termWinSize);
                }
                super.resize(termWinSize);
            }
        };
    }

    @Override
    public String runningTargetName() {
        return "DDEV web container";
    }

    @Override
    protected String getTerminalConnectionName(PtyProcess process) {
        return "DDEV web container";
    }

    private @NotNull String getShellPath() {
        return TerminalProjectOptionsProvider.getInstance(myProject).getShellPath();
    }

    private static class PtyProcessHandler extends ProcessHandler implements TaskExecutor {

        private final PtyProcess myProcess;
        private final ProcessWaitFor myWaitFor;

        PtyProcessHandler(PtyProcess process, @NotNull String presentableName) {
            myProcess = process;
            myWaitFor = new ProcessWaitFor(process, this, presentableName);
        }

        @Override
        public void startNotify() {
            addProcessListener(new ProcessAdapter() {
                @Override
                public void startNotified(@NotNull ProcessEvent event) {
                    try {
                        myWaitFor.setTerminationCallback(integer -> notifyProcessTerminated(integer));
                    } finally {
                        removeProcessListener(this);
                    }
                }
            });

            super.startNotify();
        }

        @Override
        protected void destroyProcessImpl() {
            myProcess.destroy();
        }

        @Override
        protected void detachProcessImpl() {
            destroyProcessImpl();
        }

        @Override
        public boolean detachIsDefault() {
            return false;
        }

        @Override
        public boolean isSilentlyDestroyOnClose() {
            return true;
        }

        @Nullable
        @Override
        public OutputStream getProcessInput() {
            return myProcess.getOutputStream();
        }

        @NotNull
        @Override
        public Future<?> executeTask(@NotNull Runnable task) {
            return AppExecutorUtil.getAppExecutorService().submit(task);
        }
    }

    public boolean isTerminalSessionPersistent() {
        return false;
    }
}

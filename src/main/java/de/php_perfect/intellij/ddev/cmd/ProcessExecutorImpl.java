package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import de.php_perfect.intellij.ddev.cmd.wsl.WslAware;
import org.jetbrains.annotations.NotNull;

public class ProcessExecutorImpl implements ProcessExecutor {
    public static final Logger LOG = Logger.getInstance(ProcessExecutorImpl.class.getName());

    public @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine, int timeout) throws ExecutionException {
        commandLine = WslAware.patchCommandLine(commandLine);
        final CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);
        final ProcessOutput output = processHandler.runProcess(timeout);

        if (output.getExitCode() != 0 || output.isTimeout() || output.isCancelled()) {
            LOG.info("command: " + processHandler.getCommandLine() + " has failed:" +
                    "ec=" + output.getExitCode() + ",timeout=" + output.isTimeout() + ",cancelled=" + output.isCancelled()
                    + ",stderr=" + output.getStderr() + ",stdout=" + output.getStdout());
        }

        return output;
    }
}

package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import de.php_perfect.intellij.ddev.cmd.wsl.WslAware;
import org.jetbrains.annotations.NotNull;

public class ProcessExecutorImpl implements ProcessExecutor {
    public static final Logger LOG = Logger.getInstance(ProcessExecutorImpl.class);

    public @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine, int timeout, boolean loginShell) throws ExecutionException {
        commandLine = WslAware.patchCommandLine(commandLine, loginShell);
        final CapturingProcessHandler processHandler = new CapturingProcessHandler(commandLine);
        final ProcessOutput output = processHandler.runProcess(timeout);
        LOG.debug("command: " + processHandler.getCommandLine() + " returned: " + output.toString());

        return output;
    }
}

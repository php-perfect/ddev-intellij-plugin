package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.CapturingProcessRunner;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessOutput;
import org.jetbrains.annotations.NotNull;

public class ProcessExecutorImpl implements ProcessExecutor {
    public @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine) throws ExecutionException {
        final OSProcessHandler processHandler = new OSProcessHandler(commandLine);
        final CapturingProcessRunner capturingProcessRunner = new CapturingProcessRunner(processHandler);

        return capturingProcessRunner.runProcess(5_000);
    }
}

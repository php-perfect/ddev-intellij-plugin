package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import org.jetbrains.annotations.NotNull;

public interface ProcessExecutor {
    @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine) throws ExecutionException;
}

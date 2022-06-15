package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

public interface ProcessExecutor {
    @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine, int timeout, boolean loginShell) throws ExecutionException;

    static ProcessExecutor getInstance() {
        return ApplicationManager.getApplication().getService(ProcessExecutor.class);
    }
}

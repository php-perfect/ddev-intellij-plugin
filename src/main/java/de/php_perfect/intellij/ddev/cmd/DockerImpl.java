package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;

public final class DockerImpl implements Docker {
    @Override
    public boolean isRunning(String workDirectory) {
        try {
            return ProcessExecutor.getInstance().executeCommandLine(
                    new GeneralCommandLine("docker", "info")
                            .withWorkDirectory(workDirectory)
                            .withParentEnvironmentType(GeneralCommandLine.ParentEnvironmentType.SYSTEM),
                    2_000,
                    false
            ).getExitCode() == 0;
        } catch (ExecutionException e) {
            return false;
        }
    }
}

package de.php_perfect.intellij.ddev.cmd.wsl;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.wsl.WSLCommandLineOptions;
import com.intellij.execution.wsl.WSLDistribution;
import com.intellij.execution.wsl.WslPath;
import org.jetbrains.annotations.NotNull;

public class WslAware {
    private WslAware() {
    }

    public static <T extends GeneralCommandLine> T patchCommandLine(T commandLine) {
        return patchCommandLine(commandLine, false);
    }

    public static <T extends GeneralCommandLine> T patchCommandLine(T commandLine, boolean loginShell) {
        WSLDistribution distribution = WslPath.getDistributionByWindowsUncPath(commandLine.getWorkDirectory().getPath());

        if (distribution == null) {
            return commandLine;
        }

        try {
            return applyWslPatch(commandLine, distribution, loginShell);
        } catch (ExecutionException ignored) {
            return commandLine;
        }
    }

    @NotNull
    private static <T extends GeneralCommandLine> T applyWslPatch(T generalCommandLine, WSLDistribution distribution, boolean loginShell) throws ExecutionException {
        WSLCommandLineOptions options = new WSLCommandLineOptions()
                .setExecuteCommandInLoginShell(loginShell)
                .setShellPath(distribution.getShellPath());

        return distribution.patchCommandLine(generalCommandLine, null, options);
    }
}

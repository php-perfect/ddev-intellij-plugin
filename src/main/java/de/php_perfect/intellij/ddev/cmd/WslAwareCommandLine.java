package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.openapi.util.SystemInfo;
import de.php_perfect.intellij.ddev.wsl.WslHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WslAwareCommandLine extends GeneralCommandLine {
    public WslAwareCommandLine(@Nullable String workingDirectory) {
        this(workingDirectory, List.of());
    }

    public WslAwareCommandLine(@Nullable String workingDirectory, String @NotNull ... command) {
        this(workingDirectory, List.of(command));
    }

    public WslAwareCommandLine(@Nullable String workingDirectory, @NotNull List<String> command) {
        super(wslAwareCommand(workingDirectory, command));
        this.setWorkDirectory(workingDirectory);
    }

    private static @NotNull List<String> wslAwareCommand(@Nullable String workingDirectory, @NotNull List<String> command) {
        if (!SystemInfo.isWindows) {
            return command;
        }

        String distro = WslHelper.parseWslDistro(workingDirectory);

        if (distro == null) {
            return command;
        }

        return List.of("wsl", "-d", distro, "bash", "-l", "-c", String.join(" ", command));
    }
}

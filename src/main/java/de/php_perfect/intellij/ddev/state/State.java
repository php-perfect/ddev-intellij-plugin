package de.php_perfect.intellij.ddev.state;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.version.Version;
import org.jetbrains.annotations.Nullable;

public interface State {
    boolean isBinaryConfigured();

    boolean isAvailable();

    boolean isConfigured();

    @Nullable Version getDdevVersion();

    @Nullable Description getDescription();

    @Nullable String getDdevBinary();
}

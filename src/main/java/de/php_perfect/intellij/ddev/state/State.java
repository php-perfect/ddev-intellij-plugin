package de.php_perfect.intellij.ddev.state;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.jetbrains.annotations.Nullable;

public interface State {
    boolean isInstalled();

    boolean isConfigured();

    @Nullable Versions getVersions();

    @Nullable Description getDescription();

    @Nullable String getDdevBinary();
}

package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class PhpVersion {
    private static final ExtensionPointName<de.php_perfect.intellij.ddev.PhpVersionProvider> EP_NAME = ExtensionPointName.create("de.php_perfect.intellij.ddev.phpVersionProvider");

    public static @Nullable String getLanguageLevelIfAvailable(@NotNull Project project) {
        for (de.php_perfect.intellij.ddev.PhpVersionProvider versionProvider : EP_NAME.getExtensionList()) {
            return versionProvider.getVersion(project);
        }

        return null;
    }
}

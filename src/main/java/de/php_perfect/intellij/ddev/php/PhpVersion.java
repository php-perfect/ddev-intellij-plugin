package de.php_perfect.intellij.ddev.php;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.PhpVersionProvider;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public final class PhpVersion {
    private static final ExtensionPointName<de.php_perfect.intellij.ddev.PhpVersionProvider> EP_NAME = ExtensionPointName.create("de.php_perfect.intellij.ddev.phpVersionProvider");

    public static @Nullable String getLanguageLevelIfAvailable(@NotNull Project project) {
        List<PhpVersionProvider> list = EP_NAME.getExtensionList();

        if (list.size() < 1) {
            return null;
        }

        return list.get(0).getVersion(project);

    }
}

package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface SchemaProvider {
    @Nullable VirtualFile getSchema();

    void loadSchema(@NotNull String version);

    static SchemaProvider getInstance() {
        return ApplicationManager.getApplication().getService(SchemaProvider.class);
    }
}

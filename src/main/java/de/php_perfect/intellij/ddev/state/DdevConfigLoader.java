package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface DdevConfigLoader {
    boolean exists();

    @Nullable VirtualFile load();

    static DdevConfigLoader getInstance(@NotNull Project project) {
        return project.getService(DdevConfigLoader.class);
    }
}

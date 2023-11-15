package de.php_perfect.intellij.ddev.index;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public interface ManagedConfigurationIndex {
    static ManagedConfigurationIndex getInstance(@NotNull Project project) {
        return project.getService(ManagedConfigurationIndex.class);
    }

    void set(@NonNls @NotNull String id, @NotNull Class<?> type, int hash);

    void remove(@NotNull Class<?> type);

    boolean isManaged(@NonNls @NotNull String id, @NotNull Class<?> type);

    boolean isUpToDate(@NotNull Class<?> type, int hash);
}

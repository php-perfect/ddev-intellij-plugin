package de.php_perfect.intellij.ddev.index;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface ManagedConfigurationIndex {
    static ManagedConfigurationIndex getInstance(@NotNull Project project) {
        return project.getService(ManagedConfigurationIndex.class);
    }

    void set(@NonNls @NotNull String id, @NotNull Class<? extends IndexableConfiguration> type, int hash);

    @Nullable IndexEntry get(@NotNull Class<? extends IndexableConfiguration> type);

    void remove(@NotNull Class<? extends IndexableConfiguration> type);

    boolean isManaged(@NonNls @NotNull String id, @NotNull Class<? extends IndexableConfiguration> type);

    boolean isUpToDate(@NotNull Class<? extends IndexableConfiguration> type, int hash);

    void purge();
}

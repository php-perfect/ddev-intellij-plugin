package de.php_perfect.intellij.ddev.notification;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevNotifier {
    void asyncNotifyRestartAfterSettingsChange();

    void asyncNotifyInstallDdev();

    void asyncNotifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String newVersion);

    void asyncNotifyAlreadyLatestVersion();

    static DdevNotifier getInstance(@NotNull Project project) {
        return project.getService(DdevNotifier.class);
    }
}

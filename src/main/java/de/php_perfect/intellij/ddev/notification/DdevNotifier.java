package de.php_perfect.intellij.ddev.notification;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public interface DdevNotifier {
    void asyncNotifyRestartAfterSettingsChange();

    void asyncNotifyInstallDdev();

    void asyncNotifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String newVersion);

    void asyncNotifyAlreadyLatestVersion();

    void asyncNotifyMissingPlugin(@NotNull String pluginName);

    void asyncNotifyPhpInterpreterUpdated(@NotNull String phpVersion);

    void asyncNotifyUnknownStateEntered();

    void asyncNotifyErrorReportSent(@NotNull List<String> reportIds);

    static DdevNotifier getInstance(@NotNull Project project) {
        return project.getService(DdevNotifier.class);
    }
}

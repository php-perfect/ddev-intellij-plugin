package de.php_perfect.intellij.ddev.notification;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevNotifier {
    void notifyInstallDdev();

    void notifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String newVersion);

    void notifyAlreadyLatestVersion();

    void notifyMissingPlugin(@NotNull String pluginName);

    void notifyPhpInterpreterUpdated(@NotNull String phpVersion);

    void notifyUnknownStateEntered();

    void notifyErrorReportSent(@NotNull String id);

    void notifyDdevDetected(String binary);

    void notifyDockerNotAvailable();

    static DdevNotifier getInstance(@NotNull Project project) {
        return project.getService(DdevNotifier.class);
    }
}

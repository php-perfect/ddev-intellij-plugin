package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.actions.InstallDdevAction;
import de.php_perfect.intellij.ddev.actions.InstallationInstructionsAction;
import de.php_perfect.intellij.ddev.actions.RestartIdeAction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public final class DdevNotifierImpl implements DdevNotifier {
    private final @NotNull Project project;

    public DdevNotifierImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void asyncNotifyRestartAfterSettingsChange() {
        ApplicationManager.getApplication().invokeLater(this::notifyRestartAfterSettingsChange, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyRestartAfterSettingsChange() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration.Sticky")
                .createNotification(
                        DdevIntegrationBundle.message("notification.SettingsChanged.title"),
                        DdevIntegrationBundle.message("notification.SettingsChanged.text"),
                        NotificationType.INFORMATION
                )
                .addAction(new RestartIdeAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyInstallDdev() {
        ApplicationManager.getApplication().invokeLater(this::notifyInstallDdev, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyInstallDdev() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration.Sticky")
                .createNotification(
                        DdevIntegrationBundle.message("notification.InstallDdev.title"),
                        DdevIntegrationBundle.message("notification.InstallDdev.text"),
                        NotificationType.INFORMATION
                )
                .addAction(new InstallDdevAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String latestVersion) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyNewVersionAvailable(currentVersion, latestVersion), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String latestVersion) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration.Sticky")
                .createNotification(
                        DdevIntegrationBundle.message("notification.NewVersionAvailable.title"),
                        DdevIntegrationBundle.message("notification.NewVersionAvailable.text", currentVersion, latestVersion),
                        NotificationType.INFORMATION
                )
                .addAction(new InstallationInstructionsAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyAlreadyLatestVersion() {
        ApplicationManager.getApplication().invokeLater(this::notifyAlreadyLatestVersion, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyAlreadyLatestVersion() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration.NonSticky")
                .createNotification(
                        DdevIntegrationBundle.message("notification.AlreadyLatestVersion.text"),
                        NotificationType.INFORMATION
                )
                .notify(this.project);
    }
}

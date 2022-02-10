package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.actions.DdevRestartAction;
import de.php_perfect.intellij.ddev.actions.InstallDdevAction;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.PROJECT)
public final class DdevNotifierImpl implements DdevNotifier {
    private final @NotNull Project project;

    public DdevNotifierImpl(@NotNull Project project) {
        this.project = project;
    }

    public void notifyConfigChanged() {
        final String title = "Ddev configuration changed";
        final String content = "Restart ddev to apply changes.";

        ApplicationManager.getApplication().invokeLater(() -> NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration")
                .createNotification(title, content, NotificationType.INFORMATION)
                .addAction(new DdevRestartAction())
                .notify(this.project), ModalityState.NON_MODAL);
    }

    public void notifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String latestVersion) {
        final String title = "DDEV update available";
        final String content = "Your DDEV version " + currentVersion + " is outdated. Update to " + latestVersion + " now.";

        ApplicationManager.getApplication().invokeLater(() -> NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration")
                .createNotification(title, content, NotificationType.INFORMATION)
                .addAction(new InstallDdevAction("Update"))
                .notify(this.project), ModalityState.NON_MODAL);
    }

    public void notifyAlreadyNewestVersion() {
        final String content = "You already have the latest version of DDEV installed.";

        ApplicationManager.getApplication().invokeLater(() -> NotificationGroupManager.getInstance()
                .getNotificationGroup("DdevIntegration")
                .createNotification(content, NotificationType.INFORMATION)
                .notify(this.project), ModalityState.NON_MODAL);
    }
}

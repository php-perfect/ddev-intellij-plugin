package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.actions.DdevRestartAction;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.PROJECT)
public final class DdevNotifierImpl implements DdevNotifier {

    private final @NotNull Project project;

    public DdevNotifierImpl(@NotNull Project project) {
        this.project = project;
    }

    public void notifyConfigChanged() {
        ApplicationManager.getApplication().invokeLater(() -> {
            String title = "Ddev configuration changed";
            String content = "Restart ddev to apply changes.";

            NotificationGroupManager.getInstance().getNotificationGroup("Ddev")
                    .createNotification(title, content, NotificationType.INFORMATION)
                    .addAction(new DdevRestartAction())
                    .notify(this.project);
        }, ModalityState.NON_MODAL);
    }
}

package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.application.ModalityState;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.actions.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.TestOnly;

public final class DdevNotifierImpl implements DdevNotifier {
    public static final String STICKY = "DdevIntegration.Sticky";
    public static final String NON_STICKY = "DdevIntegration.NonSticky";
    private final @NotNull Project project;

    public DdevNotifierImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void asyncNotifyInstallDdev() {
        ApplicationManager.getApplication().invokeLater(this::notifyInstallDdev, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyInstallDdev() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.InstallDdev.title"),
                        DdevIntegrationBundle.message("notification.InstallDdev.text"),
                        NotificationType.INFORMATION
                )
                .addAction(new InstallationInstructionsAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String latestVersion) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyNewVersionAvailable(currentVersion, latestVersion), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyNewVersionAvailable(@NotNull String currentVersion, @NotNull String latestVersion) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(NON_STICKY)
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
                .getNotificationGroup(NON_STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.AlreadyLatestVersion.text"),
                        NotificationType.INFORMATION
                )
                .notify(this.project);
    }

    @Override
    public void asyncNotifyMissingPlugin(@NotNull final String pluginName) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyMissingPlugin(pluginName), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyMissingPlugin(@NotNull final String pluginName) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.MissingPlugin.title"),
                        DdevIntegrationBundle.message("notification.MissingPlugin.text", pluginName),
                        NotificationType.WARNING
                )
                .addAction(new ManagePluginsAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyPhpInterpreterUpdated(@NotNull String phpVersion) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyPhpInterpreterUpdated(phpVersion), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyPhpInterpreterUpdated(@NotNull final String phpVersion) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(NON_STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.InterpreterUpdated.title"),
                        DdevIntegrationBundle.message("notification.InterpreterUpdated.text", phpVersion),
                        NotificationType.INFORMATION
                )
                .notify(this.project);
    }

    @Override
    public void asyncNotifyUnknownStateEntered() {
        ApplicationManager.getApplication().invokeLater(this::notifyUnknownStateEntered, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyUnknownStateEntered() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.UnknownStateEntered.title"),
                        DdevIntegrationBundle.message("notification.UnknownStateEntered.text"),
                        NotificationType.WARNING
                )
                .addAction(ActionManager.getInstance().getAction("DdevIntegration.SyncState"))
                .addAction(new ReportIssueAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyErrorReportSent(final @NotNull String reportId) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyErrorReportSent(reportId), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyErrorReportSent(@NotNull String reportId) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(NON_STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("errorReporting.success.title"),
                        DdevIntegrationBundle.message("errorReporting.success.text", reportId),
                        NotificationType.INFORMATION
                )
                .addAction(new ReportIssueAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyDdevDetected(String binary) {
        ApplicationManager.getApplication().invokeLater(() -> this.notifyDdevDetected(binary), ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyDdevDetected(String binary) {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(NON_STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.ddevDetected.title"),
                        DdevIntegrationBundle.message("notification.ddevDetected.text", binary),
                        NotificationType.INFORMATION
                )
                .addAction(new ChangeSettingsAction())
                .notify(this.project);
    }

    @Override
    public void asyncNotifyDockerNotAvailable() {
        ApplicationManager.getApplication().invokeLater(this::notifyDockerNotAvailable, ModalityState.NON_MODAL);
    }

    @TestOnly
    public void notifyDockerNotAvailable() {
        NotificationGroupManager.getInstance()
                .getNotificationGroup(STICKY)
                .createNotification(
                        DdevIntegrationBundle.message("notification.dockerNotAvailable.title"),
                        DdevIntegrationBundle.message("notification.dockerNotAvailable.text"),
                        NotificationType.WARNING
                )
                .addAction(new ReloadPluginAction())
                .notify(this.project);
    }
}

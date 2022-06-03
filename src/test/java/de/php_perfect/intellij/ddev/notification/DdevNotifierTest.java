package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationsManager;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DdevNotifierTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void notifyNewVersionAvailable() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyNewVersionAvailable("1.0.0", "2.0.0");

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void notifyAlreadyLatestVersion() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyAlreadyLatestVersion();

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void notifyMissingPlugin() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyMissingPlugin("Some Plugin");

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void testNotifyPhpInterpreterUpdated() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyPhpInterpreterUpdated("php99.9");

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void testNotifyUnknownStateEntered() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyUnknownStateEntered();

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void testNotifyDdevDetected() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyDdevDetected("/some/path/ddev");

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void testNotifyErrorReportSent() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyErrorReportSent("cc83481fd7b74744afdd7f36ba827f7b");

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
        assertTrue(notifications[0].getContent().contains("cc83481fd7b74744afdd7f36ba827f7b"));
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, getProject());
        for (Notification notification : notifications) {
            notificationManager.expire(notification);
        }

        super.tearDown();
    }
}

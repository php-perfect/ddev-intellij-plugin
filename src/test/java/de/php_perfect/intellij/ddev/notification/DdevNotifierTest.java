package de.php_perfect.intellij.ddev.notification;

import com.intellij.notification.Notification;
import com.intellij.notification.NotificationsManager;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.testFramework.PlatformTestUtil;
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
    public void notifyInstallDdev() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyInstallDdev();

        this.waitForEventQueue();

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    @Test
    public void notifyNewVersionAvailable() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyNewVersionAvailable("1.0.0", "2.0.0");

        this.waitForEventQueue();

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

        this.waitForEventQueue();

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

        this.waitForEventQueue();

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

        this.waitForEventQueue();

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

        this.waitForEventQueue();

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

        this.waitForEventQueue();

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

        this.waitForEventQueue();

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
        assertTrue(notifications[0].getContent().contains("cc83481fd7b74744afdd7f36ba827f7b"));
    }

    @Test
    void notifyDockerNotAvailable() {
        Project project = getProject();

        NotificationsManager notificationManager = NotificationsManager.getNotificationsManager();
        Notification[] notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertEmpty(notifications);

        new DdevNotifierImpl(project).notifyDockerNotAvailable();

        this.waitForEventQueue();

        notifications = notificationManager.getNotificationsOfType(Notification.class, project);
        assertSize(1, notifications);
    }

    private void waitForEventQueue() {
        ApplicationManager.getApplication().invokeAndWait(PlatformTestUtil::dispatchAllInvocationEventsInIdeEventQueue);
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

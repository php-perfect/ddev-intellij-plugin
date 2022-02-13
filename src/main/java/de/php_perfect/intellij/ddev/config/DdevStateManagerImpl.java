package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import de.php_perfect.intellij.ddev.event.DdevInitialisedNotifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service(Service.Level.PROJECT)
public final class DdevStateManagerImpl implements DdevStateManager, Disposable {

    private final @NotNull DdevState state = new DdevState();

    private final @NotNull Project project;

    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    public DdevStateManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    public @Nullable Versions getVersions() {
        return this.state.getVersions();
    }

    public boolean isInstalled() {
        return this.getVersions() != null;
    }

    public boolean isConfigured() {
        return this.getStatus() != null;
    }

    public @Nullable Description getStatus() {
        return this.state.getDescription();
    }

    public void updateStatus() {
        if (!this.isInstalled()) {
            return;
        }

        ApplicationManager.getApplication().executeOnPooledThread(this::loadStatus);
    }

    public void initialize() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            this.loadVersion();
            this.loadStatus();

            MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
            DdevInitialisedNotifier publisher = messageBus.syncPublisher(DdevInitialisedNotifier.DDEV_INITIALISED);
            publisher.onDdevInitialised(this.project);
        });
    }

    private void loadVersion() {
        try {
            this.state.setVersions(Ddev.getInstance(this.project).version());
        } catch (CommandFailedException ignored) {
            this.state.setVersions(null);
        }
    }

    private void loadStatus() {
        if (!this.isInstalled()) {
            return;
        }

        try {
            this.state.setDescription(Ddev.getInstance(this.project).describe());
        } catch (CommandFailedException ignored) {
            this.state.setDescription(null);
        }
    }

    public void startWatcher() {
        if (!this.isInstalled()) {
            return;
        }

        this.stopWatcher();
        this.scheduledFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(this::updateStatus, 8L, 8L, TimeUnit.SECONDS);
    }

    public void stopWatcher() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
    }

    @Override
    public void dispose() {
        this.stopWatcher();
    }
}

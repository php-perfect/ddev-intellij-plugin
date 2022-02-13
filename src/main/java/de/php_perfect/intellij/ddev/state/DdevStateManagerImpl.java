package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.event.DdevInitialisedNotifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service(Service.Level.PROJECT)
public final class DdevStateManagerImpl implements DdevStateManager, Disposable {

    private final @NotNull State state = new State();
    private final @NotNull Project project;
    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    public DdevStateManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void initialize() {
        ApplicationManager.getApplication().executeOnPooledThread(() -> {
            this.loadVersion();
            this.loadStatus();

            MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
            DdevInitialisedNotifier publisher = messageBus.syncPublisher(DdevInitialisedNotifier.DDEV_INITIALISED);
            publisher.onDdevInitialised(this.project);
        });
    }

    @Override
    public void updateState() {
        if (!this.state.isInstalled()) {
            return;
        }

        ApplicationManager.getApplication().executeOnPooledThread(this::loadStatus);
    }

    @Override
    public void startWatching() {
        if (!this.state.isInstalled()) {
            return;
        }

        this.stopWatching();
        this.scheduledFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(this::updateState, 10L, 10L, TimeUnit.SECONDS);
    }

    @Override
    public void stopWatching() {
        if (this.scheduledFuture != null) {
            this.scheduledFuture.cancel(true);
        }
    }

    @Override
    public void dispose() {
        this.stopWatching();
    }

    private void loadVersion() {
        try {
            this.state.setVersions(Ddev.getInstance(this.project).version());
        } catch (CommandFailedException ignored) {
            this.state.setVersions(null);
        }
    }

    private void loadStatus() {
        if (!this.state.isInstalled()) {
            return;
        }

        try {
            this.state.setDescription(Ddev.getInstance(this.project).describe());
        } catch (CommandFailedException ignored) {
            this.state.setDescription(null);
        }
    }
}

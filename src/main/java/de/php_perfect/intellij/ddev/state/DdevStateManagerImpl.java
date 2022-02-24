package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DdevInitialisedListener;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service(Service.Level.PROJECT)
public final class DdevStateManagerImpl implements DdevStateManager, Disposable {

    private final @NotNull StateImpl state = new StateImpl();
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
            DdevInitialisedListener publisher = messageBus.syncPublisher(DdevInitialisedListener.DDEV_INITIALISED);
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
            this.state.setVersions(Ddev.getInstance().version(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setVersions(null);
        }
    }

    private void loadStatus() {
        if (!this.state.isInstalled()) {
            this.state.setDescription(null);
            return;
        }

        try {
            Description newDescription = Ddev.getInstance().describe(this.project);
            Description currentDescription = this.state.getDescription();

            if (currentDescription == null || currentDescription.hashCode() != newDescription.hashCode()) {
                this.state.setDescription(newDescription);
                fireDdevChanged();
            }
        } catch (CommandFailedException ignored) {
            this.state.setDescription(null);
        }
    }

    private void fireDdevChanged() {
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        DdevStateChangedListener publisher = messageBus.syncPublisher(DdevStateChangedListener.DDEV_CHANGED);
        publisher.onDdevChanged(this.state);
    }
}

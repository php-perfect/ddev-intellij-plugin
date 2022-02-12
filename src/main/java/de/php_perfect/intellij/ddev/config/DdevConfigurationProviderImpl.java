package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.progress.Task;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import de.php_perfect.intellij.ddev.event.ChangeActionNotifier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

@Service(Service.Level.PROJECT)
public final class DdevConfigurationProviderImpl implements DdevConfigurationProvider, Disposable {

    private final @NotNull Project project;

    private boolean initialized;

    private @Nullable Versions versions;

    private @Nullable Description status;

    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    public DdevConfigurationProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    public @Nullable Versions getVersions() {
        return this.versions;
    }

    public boolean isInstalled() {
        return this.getVersions() != null;
    }

    public boolean isConfigured() {
        return this.getStatus() != null;
    }

    public @Nullable Description getStatus() {
        return this.status;
    }

    public void updateStatus() {
        if (!this.initialized) {
            return;
        }

        ProgressManager.getInstance().run(new Task.Backgroundable(this.project, "Checking DDEV state", true) {
            public void run(@NotNull ProgressIndicator progressIndicator) {
                DdevConfigurationProviderImpl.this.loadStatus();
            }
        });
    }

    public void initialize() {
        if (this.initialized) {
            return;
        }

        ProgressManager.getInstance().run(new Task.Backgroundable(this.project, "Checking DDEV installation", true) {
            public void run(@NotNull ProgressIndicator progressIndicator) {
                DdevConfigurationProviderImpl.this.initialized = true;
                DdevConfigurationProviderImpl.this.loadVersion();
                DdevConfigurationProviderImpl.this.loadStatus();

                MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
                ChangeActionNotifier publisher = messageBus.syncPublisher(ChangeActionNotifier.CHANGE_ACTION_TOPIC);
                publisher.onDdevInit(DdevConfigurationProviderImpl.this.project);
            }
        });
    }

    private void loadVersion() {
        try {
            this.versions = Ddev.getInstance(this.project).version();
        } catch (CommandFailedException ignored) {
            this.versions = null;
        }
    }

    private void loadStatus() {
        if (!this.isInstalled()) {
            return;
        }

        try {
            this.status = Ddev.getInstance(this.project).describe();
        } catch (CommandFailedException ignored) {
            this.status = null;
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

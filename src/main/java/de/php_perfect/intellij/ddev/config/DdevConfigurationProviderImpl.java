package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
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
        this.initialize();

        return this.versions;
    }

    public boolean isInstalled() {
        return this.getVersions() != null;
    }

    public boolean isConfigured() {
        return this.getStatus() != null;
    }

    public @Nullable Description getStatus() {
        this.initialize();

        return this.status;
    }

    public void updateStatus() {
        if (this.initialized) {
            this.loadStatus();
        } else {
            this.initialize();
        }
    }

    private synchronized void initialize() {
        if (this.initialized) {
            return;
        }

        this.initialized = true;
        this.loadVersion();
        this.loadStatus();
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

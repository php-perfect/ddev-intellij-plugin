package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.project.Project;
import com.intellij.util.concurrency.AppExecutorUtil;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

public final class StateWatcherImpl implements StateWatcher, Disposable {
    private @Nullable ScheduledFuture<?> scheduledFuture = null;

    private final @NotNull Project project;

    public StateWatcherImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void startWatching() {
        this.stopWatching();
        this.scheduledFuture = AppExecutorUtil.getAppScheduledExecutorService().scheduleWithFixedDelay(() -> {
            DdevStateManager ddevStateManager = DdevStateManager.getInstance(this.project);
            ddevStateManager.updateConfiguration();
            ddevStateManager.updateDescription();
        }, 10L, 10L, TimeUnit.SECONDS);
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
}

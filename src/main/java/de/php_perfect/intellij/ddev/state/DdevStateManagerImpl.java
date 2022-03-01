package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class DdevStateManagerImpl implements DdevStateManager {
    private final @NotNull StateImpl state = new StateImpl();
    private final @NotNull Project project;

    public DdevStateManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public @NotNull State getState() {
        return state;
    }

    @Override
    public void initialize(@Nullable Runnable afterInit) {
        this.checkChanged(() -> {
            this.checkIsInstalled();
            this.checkVersion();
            this.checkConfiguration();
            this.checkDescription();
        });

        if (afterInit != null) {
            afterInit.run();
        }
    }

    @Override
    public void updateVersion() {
        this.checkChanged(this::checkVersion);
    }

    @Override
    public void updateConfiguration() {
        this.checkChanged(this::checkConfiguration);
    }

    @Override
    public void updateDescription() {
        this.checkChanged(this::checkDescription);
    }

    private void checkChanged(Runnable runnable) {
        int oldState = this.state.hashCode();
        int oldDescription = Objects.hashCode(this.state.getDescription());

        runnable.run();

        if (oldState != this.state.hashCode()) {
            MessageBus messageBus = this.project.getMessageBus();
            messageBus.syncPublisher(DdevStateChangedListener.DDEV_CHANGED).onDdevChanged(this.state);

            if (oldDescription != Objects.hashCode(this.state.getDescription())) {
                messageBus.syncPublisher(DescriptionChangedListener.DESCRIPTION_CHANGED).onDescriptionChanged(this.state.getDescription());
            }
        }
    }

    private void checkIsInstalled() {
        try {
            this.state.setInstalled(Ddev.getInstance().isInstalled(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setInstalled(false);
        }
    }

    private void checkVersion() {
        if (!this.state.isInstalled()) {
            this.state.setVersions(null);
            return;
        }

        try {
            this.state.setVersions(Ddev.getInstance().version(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setVersions(null);
        }
    }

    private void checkConfiguration() {
        this.state.setConfigured(DdevConfigLoader.getInstance(this.project).exists());
    }

    private void checkDescription() {
        if (!this.state.isInstalled() || !this.state.isConfigured()) {
            this.state.setDescription(null);
            return;
        }

        try {
            this.state.setDescription(Ddev.getInstance().describe(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setDescription(null);
        }
    }
}

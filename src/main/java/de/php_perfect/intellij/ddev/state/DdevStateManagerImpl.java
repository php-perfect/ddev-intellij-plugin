package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DatabaseInfoChangedListener;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class DdevStateManagerImpl implements DdevStateManager {
    private static final @NotNull Logger LOG = Logger.getInstance(DdevStateManagerImpl.class.getName());
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

        LOG.info("DDEV state initialised " + this.state);
    }

    @Override
    public void updateVersion() {
        this.checkChanged(this::checkVersion);
        LOG.debug("DDEV version updated");
    }

    @Override
    public void updateConfiguration() {
        this.checkChanged(this::checkConfiguration);
        LOG.debug("DDEV configuration updated");
    }

    @Override
    public void updateDescription() {
        this.checkChanged(this::checkDescription);
        LOG.debug("DDEV description updated");
    }

    private void checkChanged(Runnable runnable) {
        final int oldState = this.state.hashCode();
        final int oldDescription = Objects.hashCode(this.state.getDescription());
        int oldDatabaseInfo = 0;
        if (this.state.getDescription() != null) {
            oldDatabaseInfo = Objects.hashCode(this.state.getDescription().getDatabaseInfo());
        }

        runnable.run();

        if (oldState != this.state.hashCode()) {
            MessageBus messageBus = this.project.getMessageBus();
            messageBus.syncPublisher(DdevStateChangedListener.DDEV_CHANGED).onDdevChanged(this.state);

            if (oldDescription != Objects.hashCode(this.state.getDescription())) {
                messageBus.syncPublisher(DescriptionChangedListener.DESCRIPTION_CHANGED).onDescriptionChanged(this.state.getDescription());

                final DatabaseInfo databaseInfo = this.state.getDescription().getDatabaseInfo();

                if (oldDatabaseInfo != Objects.hashCode(databaseInfo)) {
                    messageBus.syncPublisher(DatabaseInfoChangedListener.DATABASE_INFO_CHANGED_TOPIC).onDatabaseInfoChanged(databaseInfo);
                }
            }
        }
    }

    private void checkIsInstalled() {
        try {
            String ddevBinary = Ddev.getInstance().findBinary(this.project);

            this.state.setInstalled(ddevBinary != null);
            this.state.setDdevBinary(ddevBinary);
        } catch (CommandFailedException ignored) {
            this.state.setInstalled(false);
            this.state.setDdevBinary(null);
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

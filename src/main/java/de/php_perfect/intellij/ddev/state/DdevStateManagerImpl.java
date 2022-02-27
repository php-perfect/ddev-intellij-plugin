package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Service(Service.Level.PROJECT)
public final class DdevStateManagerImpl implements DdevStateManager {

    private final @NotNull StateImpl state = new StateImpl();
    private final @NotNull Project project;

    public DdevStateManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    @Override
    public State getState() {
        return state;
    }

    @Override
    public void initialize(@Nullable Runnable afterInit) {
        this.loadVersion();
        this.updateDescription();
        if (afterInit != null) {
            afterInit.run();
        }
    }

    @Override
    public void updateVersion() {
        // @todo Events
        this.loadVersion();
    }

    @Override
    public void updateDescription() {
        Description newDescription = null;

        if (this.state.isInstalled()) {
            try {
                newDescription = Ddev.getInstance().describe(this.project);
            } catch (CommandFailedException ignored) {
            }
        }

        if (!Objects.equals(this.state.getDescription(), newDescription)) {
            MessageBus messageBus = this.project.getMessageBus();

            this.state.setDescription(newDescription);
            messageBus.syncPublisher(DescriptionChangedListener.DESCRIPTION_CHANGED).onDescriptionChanged(newDescription);
            messageBus.syncPublisher(DdevStateChangedListener.DDEV_CHANGED).onDdevChanged(this.state);
        }
    }

    private void loadVersion() {
        Ddev ddev = Ddev.getInstance();

        try {
            this.state.setInstalled(ddev.isInstalled(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setInstalled(false);
            this.state.setVersions(null);
            return;
        }

        try {
            this.state.setVersions(ddev.version(this.project));
        } catch (CommandFailedException ignored) {
            this.state.setVersions(null);
        }
    }
}

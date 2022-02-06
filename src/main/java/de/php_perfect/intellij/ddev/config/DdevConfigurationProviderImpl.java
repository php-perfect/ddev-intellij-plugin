package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.DdevCmdException;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@Service(Service.Level.PROJECT)
public final class DdevConfigurationProviderImpl implements DdevConfigurationProvider {

    @NotNull
    private final Project project;

    @Nullable
    private Description configuration;

    public DdevConfigurationProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    @NotNull
    public Description getDdevConfig() throws DdevConfigurationException {
        if (this.configuration == null) {
            this.update();
        }

        return configuration;
    }

    public void update() throws DdevConfigurationException {
        try {
            this.configuration = Ddev.getInstance(this.project).describe();
        } catch (DdevCmdException exception) {
            throw new DdevConfigurationException("Could not update Configuration", exception);
        }
    }
}

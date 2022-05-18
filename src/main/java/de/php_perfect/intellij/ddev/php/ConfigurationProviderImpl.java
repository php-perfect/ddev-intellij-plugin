package de.php_perfect.intellij.ddev.php;

import com.intellij.ide.plugins.PluginManager;
import com.intellij.openapi.extensions.PluginId;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ConfigurationProviderImpl implements ConfigurationProvider {
    private static final List<String> REQUIRED_PLUGINS = List.of(
            "org.jetbrains.plugins.phpstorm-remote-interpreter",
            "org.jetbrains.plugins.phpstorm-docker",
            "Docker"
    );

    private final @NotNull Project project;

    public ConfigurationProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void configure(@NotNull Description description) {
        if (!DdevSettingsState.getInstance(this.project).autoConfigurePhpInterpreter) {
            return;
        }

        if (description.getName() == null || description.getPhpVersion() == null) {
            return;
        }

        final VirtualFile composeFile = DdevComposeFileLoader.getInstance(this.project).load();

        if (composeFile == null || !composeFile.exists()) {
            return;
        }

        final var pluginManager = PluginManager.getInstance();

        for (final String id : REQUIRED_PLUGINS) {
            final PluginId pluginId = PluginId.findId(id);

            if (pluginId == null || pluginManager.findEnabledPlugin(pluginId) == null) {
                DdevNotifier.getInstance(this.project).asyncNotifyMissingPlugin(id);
                return;
            }
        }

        final DdevInterpreterConfig ddevInterpreterConfig = new DdevInterpreterConfig(description.getName(), "php" + description.getPhpVersion(), composeFile.getPath());
        PhpInterpreterProvider.getInstance(this.project).registerInterpreter(ddevInterpreterConfig);
    }
}

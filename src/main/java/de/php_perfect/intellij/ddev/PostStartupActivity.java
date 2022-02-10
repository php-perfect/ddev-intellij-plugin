package de.php_perfect.intellij.ddev;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.startup.StartupActivity;
import de.php_perfect.intellij.ddev.config.DdevConfigurationProvider;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import de.php_perfect.intellij.ddev.version.VersionChecker;
import org.jetbrains.annotations.NotNull;

// TODO: 06.02.2022 Tidy up after testing
public class PostStartupActivity implements StartupActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DdevNotifier.getInstance(project).notifyConfigChanged();
        DdevConfigurationProvider ddevConfigurationProvider = DdevConfigurationProvider.getInstance(project);

        System.out.println(ddevConfigurationProvider.isInstalled());
        System.out.println(ddevConfigurationProvider.getVersions());
        System.out.println(ddevConfigurationProvider.isConfigured());
        System.out.println(ddevConfigurationProvider.getStatus());

        if (DdevSettingsState.getInstance().watchDdev) {
            ddevConfigurationProvider.startWatcher();
        }

        if (DdevSettingsState.getInstance().checkForUpdates) {
            VersionChecker.getInstance(project).checkDdevVersion();
        }
    }
}

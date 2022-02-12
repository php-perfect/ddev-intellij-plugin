package de.php_perfect.intellij.ddev.activities;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.config.DdevConfigurationProvider;
import de.php_perfect.intellij.ddev.settings.DdevSettingsState;
import de.php_perfect.intellij.ddev.version.VersionChecker;
import org.jetbrains.annotations.NotNull;

public final class CheckVersionActivity implements DdevAwareActivity {
    @Override
    public void runActivity(@NotNull Project project) {
        DdevConfigurationProvider ddevConfigurationProvider = DdevConfigurationProvider.getInstance(project);

        if (DdevSettingsState.getInstance(project).checkForUpdates) {
            VersionChecker.getInstance(project).checkDdevVersion();
        }

        System.out.println(ddevConfigurationProvider.isInstalled());
        System.out.println(ddevConfigurationProvider.getVersions());
        System.out.println(ddevConfigurationProvider.isConfigured());
        System.out.println(ddevConfigurationProvider.getStatus());
    }
}

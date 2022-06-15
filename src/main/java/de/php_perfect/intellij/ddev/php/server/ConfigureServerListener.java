package de.php_perfect.intellij.ddev.php.server;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.MalformedURLException;
import java.net.URL;

public final class ConfigureServerListener implements DescriptionChangedListener {
    private final @NotNull Project project;

    public ConfigureServerListener(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void onDescriptionChanged(@Nullable Description description) {
        String localPath = this.project.getBasePath();

        if (description == null || localPath == null) {
            return;
        }

        if (description.getPrimaryUrl() == null) {
            return;
        }

        URL url;
        try {
            url = new URL(description.getPrimaryUrl());
        } catch (MalformedURLException ignored) {
            return;
        }

        ServerConfig serverConfig = new ServerConfig(localPath, "/var/www/html", url);
        ServerConfigManager.getInstance(this.project).configure(serverConfig);
    }
}

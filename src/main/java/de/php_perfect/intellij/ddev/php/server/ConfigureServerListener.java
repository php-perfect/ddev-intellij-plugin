package de.php_perfect.intellij.ddev.php.server;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.net.URI;
import java.net.URISyntaxException;

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


        URI uri;
        try {
            uri = new URI(description.getPrimaryUrl());
        } catch (URISyntaxException ignored) {
            return;
        }

        ServerConfig serverConfig = new ServerConfig(localPath, "/var/www/html", uri);
        ServerConfigManager.getInstance(this.project).configure(serverConfig);
    }
}

package de.php_perfect.intellij.ddev.php.server;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.servers.PhpServer;
import com.jetbrains.php.config.servers.PhpServersWorkspaceStateComponent;
import de.php_perfect.intellij.ddev.index.ManagedConfigurationIndex;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ServerConfigManagerImpl implements ServerConfigManager {
    private static final @NotNull Logger LOG = Logger.getInstance(ServerConfigManagerImpl.class);

    private final @NotNull Project project;

    public ServerConfigManagerImpl(final @NotNull Project project) {
        this.project = project;
    }

    public void configure(final @NotNull ServerConfig serverConfig) {
        final int hash = serverConfig.hashCode();
        final String fqdn = serverConfig.getUrl().getHost();
        final ManagedConfigurationIndex managedConfigurationIndex = ManagedConfigurationIndex.getInstance(this.project);

        if (managedConfigurationIndex.isUpToDate(PhpServer.class, hash)) {
            LOG.debug(String.format("server configuration %s is up to date", fqdn));
            return;
        }

        LOG.debug(String.format("Updating server configuration %s", fqdn));

        final List<PhpServer> servers = PhpServersWorkspaceStateComponent.getInstance(project).getServers();

        PhpServer phpServer = servers.stream()
                .filter(currentPhpServer -> managedConfigurationIndex.isManaged(currentPhpServer.getId(), PhpServer.class))
                .findFirst()
                .orElse(null);

        if (phpServer == null) {
            phpServer = new PhpServer();
            servers.add(phpServer);
        }

        phpServer.setName(fqdn);
        phpServer.setHost(fqdn);
        phpServer.setPort(80);
        phpServer.setUsePathMappings(true);

        final List<PathMappingSettings.PathMapping> mappings = phpServer.getMappings();
        final PathMappingSettings.PathMapping mapping = new PathMappingSettings.PathMapping(serverConfig.getLocalPath(), serverConfig.getRemotePath());
        mappings.clear();
        mappings.add(mapping);

        managedConfigurationIndex.set(phpServer.getId(), PhpServer.class, hash);
    }
}

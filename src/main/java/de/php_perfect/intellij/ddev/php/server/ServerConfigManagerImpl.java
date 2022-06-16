package de.php_perfect.intellij.ddev.php.server;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.servers.PhpServer;
import com.jetbrains.php.config.servers.PhpServersWorkspaceStateComponent;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class ServerConfigManagerImpl implements ServerConfigManager {
    private static final @NotNull Logger LOG = Logger.getInstance(ServerConfigManagerImpl.class);

    private static final @NotNull String SERVER_NAME = "DDEV";

    private final @NotNull Project project;

    public ServerConfigManagerImpl(final @NotNull Project project) {
        this.project = project;
    }

    public void configure(final @NotNull ServerConfig serverConfig) {
        LOG.debug(String.format("Updating server configuration %s", SERVER_NAME));
        this.addOrReplaceServer(
                PhpServersWorkspaceStateComponent.getInstance(project).getServers(),
                createServerConfiguration(serverConfig)
        );
    }

    private @NotNull PhpServer createServerConfiguration(final @NotNull ServerConfig serverConfig) {
        final PhpServer phpServer = new PhpServer();
        phpServer.setName(SERVER_NAME);
        phpServer.setHost(serverConfig.getUrl().getHost());
        phpServer.setUsePathMappings(true);
        phpServer.getMappings().add(new PathMappingSettings.PathMapping(serverConfig.getLocalPath(), serverConfig.getRemotePath()));

        return phpServer;
    }

    private void addOrReplaceServer(final @NotNull List<PhpServer> servers, final @NotNull PhpServer server) {
        servers.removeIf(phpServer -> StringUtil.equals(phpServer.getName(), server.getName()));
        servers.add(server);
    }
}

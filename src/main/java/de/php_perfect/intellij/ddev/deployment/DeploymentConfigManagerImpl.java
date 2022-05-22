package de.php_perfect.intellij.ddev.deployment;

import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Pair;
import com.jetbrains.plugins.webDeployment.config.*;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public final class DeploymentConfigManagerImpl implements DeploymentConfigManager {
    private static final @NotNull Logger LOG = Logger.getInstance(DeploymentConfigManagerImpl.class);

    private final static @NotNull String CONFIG_NAME = "DDEV";

    private final @NotNull Project project;

    public DeploymentConfigManagerImpl(@NotNull Project project) {
        this.project = project;
    }

    public void configure(@NotNull DeploymentConfig deploymentConfig) {
        final WebServerConfig webServerConfig = loadWebServerConfig();

        webServerConfig.setIsProjectLevel(true);
        webServerConfig.setName(CONFIG_NAME);
        webServerConfig.setUrl(deploymentConfig.getUrl());
        webServerConfig.getFileTransferConfig().setAccessType(AccessType.MOUNT);
        webServerConfig.getFileTransferConfig().setMountedFolder(deploymentConfig.getLocalPath());

        final var deploymentPathMapping = new DeploymentPathMapping();
        deploymentPathMapping.setLocalPath(deploymentConfig.getLocalPath());
        deploymentPathMapping.setDeployPath(deploymentConfig.getDeployPath());
        deploymentPathMapping.setWebPath(deploymentConfig.getWebPath());

        this.updateServer(webServerConfig);

        final var publishConfig = PublishConfig.getInstance(project);
        publishConfig.setPathMappings(webServerConfig.getId(), List.of(deploymentPathMapping));

        LOG.info(String.format("Updated deployment configuration %s %s", CONFIG_NAME, deploymentConfig));

        if (publishConfig.getDefaultServerOrGroupName() == null) {
            LOG.info(String.format("Setting %s deployment configuration as default", CONFIG_NAME));
            publishConfig.setDefaultGroupOrServerName(webServerConfig.getName());
        }
    }

    private WebServerConfig loadWebServerConfig() {
        final GroupedServersConfigManager serverManager = GroupedServersConfigManager.getInstance(project);
        final Pair<WebServerGroupingWrap, WebServerConfig> webServerConfigPair = serverManager.findByName(CONFIG_NAME);

        if (webServerConfigPair != null) {
            LOG.debug(String.format("Editing existing WebServerConfig for Name %s", CONFIG_NAME));

            return webServerConfigPair.getSecond();
        }

        LOG.debug(String.format("Creating new WebServerConfig for Name %s", CONFIG_NAME));

        return new WebServerConfig(WebServerConfig.getNextId());
    }

    private void updateServer(WebServerConfig webServerConfig) {
        final GroupedServersConfigManager serverManager = GroupedServersConfigManager.getInstance(project);

        if (serverManager.findServer(webServerConfig.getId()) != null) {
            serverManager.removeServer(webServerConfig.getId());
        }

        serverManager.addServer(webServerConfig);
    }
}

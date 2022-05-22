package de.php_perfect.intellij.ddev.deployment;

import com.intellij.openapi.util.Pair;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.jetbrains.plugins.webDeployment.config.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DeploymentConfigManagerImplTest extends BasePlatformTestCase {
    private static final @NotNull String DEPLOYMENT_NAME = "DDEV";

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void testAddNewDeploymentConfig() {
        final DeploymentConfigManager webServerConfigManager = DeploymentConfigManager.getInstance(this.getProject());

        final DeploymentConfig deploymentConfig = new DeploymentConfig(
                "C:\\Users\\test\\AppData\\Local\\Temp\\my-project",
                "/var/www/html",
                "/",
                "https://test.ddev.site"
        );

        webServerConfigManager.configure(deploymentConfig);

        assertDeploymentEqualsConfig(deploymentConfig);
        assertDefaultDeployment(DEPLOYMENT_NAME);
    }

    @Test
    public void testUpdateDeploymentConfig() {
        final DeploymentConfigManager webServerConfigManager = DeploymentConfigManager.getInstance(this.getProject());

        final DeploymentConfig deploymentConfig = new DeploymentConfig(
                "C:\\Users\\test\\AppData\\Local\\Temp\\my-project",
                "/var/www/html",
                "/",
                "https://test.ddev.site"
        );

        webServerConfigManager.configure(deploymentConfig);

        final DeploymentConfig newDeploymentConfig = new DeploymentConfig(
                "C:\\Users\\test\\AppData\\Local\\Temp\\my-project",
                "/srv/html",
                "/test/",
                "https://test2.ddev.site"
        );

        webServerConfigManager.configure(newDeploymentConfig);

        asssertConfigurationIsReplaced();
        assertDeploymentEqualsConfig(newDeploymentConfig);
        assertDefaultDeployment(DEPLOYMENT_NAME);
    }

    @Test
    public void testDefaultDeploymentIsNotOverridden() {
        final PublishConfig publishConfig = PublishConfig.getInstance(this.getProject());
        publishConfig.setDefaultGroupOrServerName("Default");

        final DeploymentConfigManager webServerConfigManager = DeploymentConfigManager.getInstance(this.getProject());

        final DeploymentConfig deploymentConfig = new DeploymentConfig(
                "C:\\Users\\test\\AppData\\Local\\Temp\\my-project",
                "/var/www/html",
                "/",
                "https://test.ddev.site"
        );

        webServerConfigManager.configure(deploymentConfig);

        assertDefaultDeployment("Default");
    }

    private void assertDeploymentEqualsConfig(DeploymentConfig deploymentConfig) {
        final var serverManager = GroupedServersConfigManager.getInstance(this.getProject());
        Pair<WebServerGroupingWrap, WebServerConfig> webServerConfigPair = serverManager.findByName(DEPLOYMENT_NAME);

        Assertions.assertNotNull(webServerConfigPair);

        final var webServerConfig = webServerConfigPair.second;

        Assertions.assertEquals(DEPLOYMENT_NAME, webServerConfig.getName());
        Assertions.assertTrue(webServerConfig.isProjectLevel());
        Assertions.assertEquals(deploymentConfig.getLocalPath(), webServerConfig.getMountedFolder());
        Assertions.assertEquals(deploymentConfig.getUrl(), webServerConfig.getUrl());
        Assertions.assertInstanceOf(AccessType.MOUNT.getClass(), webServerConfig.getAccessType());

        final PublishConfig publishConfig = PublishConfig.getInstance(this.getProject());
        final var mappings = publishConfig.getPathMappings(webServerConfig.getId());
        final var stream = mappings.stream();
        final var optional = stream.findFirst();

        Assertions.assertTrue(optional.isPresent());

        final DeploymentPathMapping deploymentPathMapping = optional.get();

        Assertions.assertEquals(deploymentConfig.getLocalPath(), deploymentPathMapping.getLocalPath());
        Assertions.assertEquals(deploymentConfig.getDeployPath(), deploymentPathMapping.getDeployPath());
        Assertions.assertEquals(deploymentConfig.getWebPath(), deploymentPathMapping.getWebPath());
    }

    private void assertDefaultDeployment(@Nullable String expectedName) {
        final var publishConfig = PublishConfig.getInstance(this.getProject());

        Assertions.assertEquals(expectedName, publishConfig.getDefaultServerOrGroupName());
    }

    private void asssertConfigurationIsReplaced() {
        final var serverManager = GroupedServersConfigManager.getInstance(this.getProject());
        Assertions.assertEquals(1, serverManager.getFlattenedServers().size());
    }
}

package de.php_perfect.intellij.ddev.php;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.openapi.project.Project;
import com.intellij.remote.RemoteMappingsManager;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.composer.ComposerDataService;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.jetbrains.php.config.interpreters.PhpInterpretersPhpInfoCacheImpl;
import com.jetbrains.php.config.phpInfo.PhpInfo;
import com.jetbrains.php.config.phpInfo.PhpInfoUtil;
import com.jetbrains.php.remote.composer.ComposerRemoteInterpreterExecution;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import de.php_perfect.intellij.ddev.dockerCompose.DockerComposeCredentialProvider;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

public final class PhpInterpreterProviderImpl implements PhpInterpreterProvider {
    private static final String NAME = "DDEV";
    private static final String HELPERS_DIR = "/opt/.phpstorm_helpers";

    private final @NotNull Project project;

    public PhpInterpreterProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    public void registerInterpreter(@NotNull DdevInterpreterConfig interpreterConfig) {
        final PhpInterpreter interpreter = this.getDdevPhpInterpreter();

        if (!this.needsUpdate(interpreter, interpreterConfig)) {
            return;
        }

        this.updateInterpreter(interpreter, interpreterConfig);
        this.loadPhpInfo(interpreter);
        this.setDefaultIfNotSet(interpreter);
        this.updateComposerInterpreterIfNotSet(interpreter);
        this.updateRemoteMapping(interpreter);

        DdevNotifier.getInstance(project).notifyPhpInterpreterUpdated(interpreterConfig.phpVersion());
    }

    private void updateRemoteMapping(PhpInterpreter interpreter) {
        final var pathMapping = new PathMappingSettings.PathMapping(project.getBasePath(), "/var/www/html");
        final var mappings = new RemoteMappingsManager.Mappings();
        mappings.setServerId("php", interpreter.getId());
        mappings.setSettings(List.of(pathMapping));

        RemoteMappingsManager.getInstance(project).setForServer(mappings);
    }

    private void updateComposerInterpreterIfNotSet(final PhpInterpreter interpreter) {
        final ComposerDataService composerSettings = ComposerDataService.getInstance(project);

        if (!Objects.equals(composerSettings.getComposerExecution().getInterpreterId(), interpreter.getId())) {
            composerSettings.setComposerExecution(new ComposerRemoteInterpreterExecution(interpreter.getName(), "composer"));
        }
    }

    private @NotNull PhpInterpreter getDdevPhpInterpreter() {
        final PhpInterpretersManagerImpl interpretersManager = PhpInterpretersManagerImpl.getInstance(this.project);

        PhpInterpreter interpreter = interpretersManager.findInterpreter(NAME);

        if (interpreter == null) {
            interpreter = createInterpreter();

            List<PhpInterpreter> interpreters = interpretersManager.getInterpreters();
            interpreters.add(interpreter);
            interpretersManager.setInterpreters(interpreters);
        }

        return interpreter;
    }

    private @NotNull PhpInterpreter createInterpreter() {
        final PhpInterpreter interpreter = new PhpInterpreter();
        interpreter.setName(NAME);
        interpreter.setIsProjectLevel(true);

        return interpreter;
    }

    private boolean needsUpdate(@NotNull PhpInterpreter existingInterpreter, @NotNull DdevInterpreterConfig interpreterConfig) {
        return !(Objects.equals(existingInterpreter.getPathToPhpExecutable(), interpreterConfig.phpVersion()));
    }

    private void updateInterpreter(@NotNull PhpInterpreter interpreter, @NotNull DdevInterpreterConfig interpreterConfig) {
        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialProvider.getInstance().getDdevDockerComposeCredentials(List.of(interpreterConfig.composeFilePath()), interpreterConfig.name());
        final PhpRemoteSdkAdditionalData sdkData = this.buildSdkAdditionalData(interpreter, interpreterConfig, credentials);
        interpreter.setPhpSdkAdditionalData(sdkData);
        interpreter.setHomePath(sdkData.getSdkId());
    }

    private @NotNull PhpRemoteSdkAdditionalData buildSdkAdditionalData(@NotNull PhpInterpreter interpreter, @NotNull DdevInterpreterConfig interpreterConfig, DockerComposeCredentialsHolder credentials) {
        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData(interpreterConfig.phpVersion());
        sdkData.setInterpreterId(interpreter.getId());
        sdkData.setHelpersPath(HELPERS_DIR);
        sdkData.setTypeData(new PhpDockerComposeTypeData(PhpDockerComposeStartCommand.EXEC));
        sdkData.setCredentials(DockerComposeCredentialsType.getInstance().getCredentialsKey(), credentials);
        sdkData.setPathMappings(this.loadPathMappings(sdkData));

        return sdkData;
    }

    private PathMappingSettings loadPathMappings(PhpRemoteSdkAdditionalData sdkData) {
        final PhpRemoteInterpreterManager phpRemoteInterpreterManager = PhpRemoteInterpreterManager.getInstance();

        if (phpRemoteInterpreterManager != null) {
            return phpRemoteInterpreterManager.createPathMappings(this.project, sdkData);
        }

        return null;
    }

    private void loadPhpInfo(final PhpInterpreter interpreter) {
        final PhpInfo phpInfo = PhpInfoUtil.getPhpInfo(this.project, interpreter, null);
        PhpInterpretersPhpInfoCacheImpl.getInstance(this.project).setPhpInfo(interpreter.getName(), phpInfo);
    }

    private void setDefaultIfNotSet(final PhpInterpreter interpreter) {
        final var phpConfigurationFacade = PhpProjectConfigurationFacade.getInstance(this.project);
        final var phpConfiguration = phpConfigurationFacade.getProjectConfiguration();

        if (phpConfiguration.getInterpreterName() == null) {
            phpConfiguration.setInterpreterName(interpreter.getName());
        }
    }
}

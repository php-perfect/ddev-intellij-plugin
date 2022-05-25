package de.php_perfect.intellij.ddev.php;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.docker.remote.DockerCredentialsEditor;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.openapi.project.Project;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.PhpProjectConfigurationFacade;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.jetbrains.php.config.interpreters.PhpInterpretersPhpInfoCacheImpl;
import com.jetbrains.php.config.phpInfo.PhpInfo;
import com.jetbrains.php.config.phpInfo.PhpInfoUtil;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public final class PhpInterpreterProviderImpl implements PhpInterpreterProvider {
    private static final String NAME = "DDEV";
    private static final String HELPERS_DIR = "/opt/.phpstorm_helpers";
    private static final String DOCKER_NAME = "Docker";
    private static final String SERVICE_NAME = "web";
    private static final String COMPOSE_PROJECT_NAME_ENV = "COMPOSE_PROJECT_NAME";

    private final @NotNull Project project;

    public PhpInterpreterProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    public void registerInterpreter(@NotNull DdevInterpreterConfig interpreterConfig) {
        final PhpInterpreter interpreter = getDdevPhpInterpreter();

        if (!this.needsUpdate(interpreter, interpreterConfig)) {
            return;
        }

        this.updateInterpreter(interpreter, interpreterConfig);
        this.loadPhpInfo(interpreter);
        this.setDefaultIfNotSet(interpreter);

        DdevNotifier.getInstance(project).asyncNotifyPhpInterpreterUpdated(interpreterConfig.getPhpVersion());
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
        return !(Objects.equals(existingInterpreter.getPathToPhpExecutable(), interpreterConfig.getPhpVersion()));
    }

    private void updateInterpreter(@NotNull PhpInterpreter interpreter, @NotNull DdevInterpreterConfig interpreterConfig) {
        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData(interpreterConfig.getPhpVersion());
        sdkData.setInterpreterId(interpreter.getId());
        sdkData.setHelpersPath(HELPERS_DIR);

        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialsType.getInstance().createCredentials();
        credentials.setAccountName(DOCKER_NAME);
        credentials.setComposeFilePaths(List.of(interpreterConfig.getComposeFilePath()));
        credentials.setComposeServiceName(SERVICE_NAME);
        credentials.setRemoteProjectPath(DockerCredentialsEditor.DEFAULT_DOCKER_PROJECT_PATH);
        credentials.setEnvs(EnvironmentVariablesData.create(Map.of(COMPOSE_PROJECT_NAME_ENV, "ddev-" + interpreterConfig.getName()), true));

        sdkData.setTypeData(new PhpDockerComposeTypeData(PhpDockerComposeStartCommand.EXEC));
        sdkData.setCredentials(DockerComposeCredentialsType.getInstance().getCredentialsKey(), credentials);
        sdkData.setPathMappings(this.loadPathMappings(sdkData));

        interpreter.setPhpSdkAdditionalData(sdkData);
        interpreter.setHomePath(sdkData.getSdkId());
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

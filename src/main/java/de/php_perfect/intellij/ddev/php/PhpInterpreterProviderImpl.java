package de.php_perfect.intellij.ddev.php;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.docker.remote.DockerCredentialsEditor;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.openapi.project.Project;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.jetbrains.php.config.interpreters.PhpInterpretersPhpInfoCacheImpl;
import com.jetbrains.php.config.interpreters.PhpSdkAdditionalData;
import com.jetbrains.php.config.phpInfo.PhpInfo;
import com.jetbrains.php.config.phpInfo.PhpInfoUtil;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PhpInterpreterProviderImpl implements PhpInterpreterProvider {
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
        final PhpInterpretersManagerImpl interpretersManager = PhpInterpretersManagerImpl.getInstance(this.project);
        PhpInterpreter interpreter = interpretersManager.findInterpreter(NAME);

        if (interpreter == null) {
            interpreter = createInterpreter();

            List<PhpInterpreter> interpreters = interpretersManager.getInterpreters();
            interpreters.add(interpreter);
            interpretersManager.setInterpreters(interpreters);
        }

        if (!this.needsUpdate(interpreter, interpreterConfig)) {
            System.out.println("Needs no update");
            return;
        }

        System.out.println("Updating!");
        this.updateInterpreter(interpreter, interpreterConfig);
        final PhpInfo phpInfo = PhpInfoUtil.getPhpInfo(this.project, interpreter, null);
        PhpInterpretersPhpInfoCacheImpl.getInstance(this.project).setPhpInfo(interpreter.getName(), phpInfo);
    }

    private @NotNull PhpInterpreter createInterpreter() {
        PhpInterpreter interpreter;
        interpreter = new PhpInterpreter();
        interpreter.setName(NAME);
        interpreter.setIsProjectLevel(true);

        return interpreter;
    }

    private boolean needsUpdate(@NotNull PhpInterpreter existingInterpreter, @NotNull DdevInterpreterConfig interpreterConfig) {
        PhpSdkAdditionalData additionalData = existingInterpreter.getPhpSdkAdditionalData();

        if (!(additionalData instanceof PhpRemoteSdkAdditionalData)) {
            return true;
        }

        return !((PhpRemoteSdkAdditionalData) additionalData).getInterpreterPath().equals("php" + interpreterConfig.getPhpVersion());
    }

    private void updateInterpreter(@NotNull PhpInterpreter interpreter, @NotNull DdevInterpreterConfig interpreterConfig) {
        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData("php" + interpreterConfig.getPhpVersion());
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
    }

    private PathMappingSettings loadPathMappings(PhpRemoteSdkAdditionalData sdkData) {
        final PhpRemoteInterpreterManager phpRemoteInterpreterManager = PhpRemoteInterpreterManager.getInstance();

        if (phpRemoteInterpreterManager != null) {
            return phpRemoteInterpreterManager.createPathMappings(this.project, sdkData);
        }

        return null;
    }
}

package de.php_perfect.intellij.ddev.php;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.docker.remote.DockerCredentialsEditor;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.openapi.project.Project;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    @Override
    public @Nullable PhpInterpreter buildDdevPhpInterpreter(@NotNull Description description, @NotNull String composeFilePath) {
        PhpInterpreter phpInterpreter = new PhpInterpreter();
        phpInterpreter.setName(NAME);
        phpInterpreter.setIsProjectLevel(true);

        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData("php" + description.getPhpVersion());
        sdkData.setInterpreterId(phpInterpreter.getId());
        sdkData.setHelpersPath(HELPERS_DIR);

        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialsType.getInstance().createCredentials();
        credentials.setAccountName(DOCKER_NAME);
        credentials.setComposeFilePaths(List.of(composeFilePath));
        credentials.setComposeServiceName(SERVICE_NAME);
        credentials.setRemoteProjectPath(DockerCredentialsEditor.DEFAULT_DOCKER_PROJECT_PATH);
        credentials.setEnvs(EnvironmentVariablesData.create(Map.of(COMPOSE_PROJECT_NAME_ENV, "ddev-" + description.getName()), true));

        sdkData.setTypeData(new PhpDockerComposeTypeData(PhpDockerComposeStartCommand.EXEC));
        sdkData.setCredentials(DockerComposeCredentialsType.getInstance().getCredentialsKey(), credentials);
        sdkData.setPathMappings(this.loadPathMappings(sdkData));

        phpInterpreter.setPhpSdkAdditionalData(sdkData);

        return phpInterpreter;
    }

    private PathMappingSettings loadPathMappings(PhpRemoteSdkAdditionalData sdkData) {
        final PhpRemoteInterpreterManager phpRemoteInterpreterManager = PhpRemoteInterpreterManager.getInstance();

        if (phpRemoteInterpreterManager != null) {
            return phpRemoteInterpreterManager.createPathMappings(this.project, sdkData);
        }

        return null;
    }
}

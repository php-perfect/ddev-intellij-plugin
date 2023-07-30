package de.php_perfect.intellij.ddev.dockerCompose;

import com.intellij.docker.DockerCloudType;
import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.docker.remote.DockerCredentialsEditor;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.remoteServer.configuration.RemoteServersManager;

import java.util.List;
import java.util.Map;

public final class DockerComposeCredentialProviderImpl implements DockerComposeCredentialProvider {
    private static final String DOCKER_NAME = "Docker";
    private static final String SERVICE_NAME = "web";
    private static final String COMPOSE_PROJECT_NAME_ENV = "COMPOSE_PROJECT_NAME";

    public DockerComposeCredentialsHolder getDdevDockerComposeCredentials(List<String> composeFilePaths, String projectName) {
        this.ensureDockerRemoteServer();

        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialsType.getInstance().createCredentials();
        credentials.setAccountName(DOCKER_NAME);
        credentials.setComposeFilePaths(composeFilePaths);
        credentials.setComposeServiceName(SERVICE_NAME);
        credentials.setRemoteProjectPath(DockerCredentialsEditor.DEFAULT_DOCKER_PROJECT_PATH);
        credentials.setEnvs(EnvironmentVariablesData.create(Map.of(COMPOSE_PROJECT_NAME_ENV, "ddev-" + projectName), true));

        return credentials;
    }

    private void ensureDockerRemoteServer() {
        final var type = DockerCloudType.getInstance();
        final var remoteServerManager = RemoteServersManager.getInstance();

        if (remoteServerManager.findByName(DOCKER_NAME, type) == null) {
            remoteServerManager.addServer(remoteServerManager.createServer(type, DOCKER_NAME));
        }
    }
}

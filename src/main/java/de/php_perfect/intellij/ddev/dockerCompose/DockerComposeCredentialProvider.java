package de.php_perfect.intellij.ddev.dockerCompose;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.openapi.application.ApplicationManager;

import java.util.List;

public interface DockerComposeCredentialProvider {
    DockerComposeCredentialsHolder getDdevDockerComposeCredentials(List<String> composeFilePaths, String projectName);

    static DockerComposeCredentialProvider getInstance() {
        return ApplicationManager.getApplication().getService(DockerComposeCredentialProvider.class);
    }
}

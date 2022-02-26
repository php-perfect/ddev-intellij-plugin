package de.php_perfect.intellij.ddev.php;

import com.intellij.docker.remote.DockerComposeCredentialsHolder;
import com.intellij.docker.remote.DockerComposeCredentialsType;
import com.intellij.docker.remote.DockerCredentialsEditor;
import com.intellij.execution.configuration.EnvironmentVariablesData;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.util.PathMappingSettings;
import com.jetbrains.php.config.interpreters.PhpInterpreter;
import com.jetbrains.php.config.interpreters.PhpInterpretersManagerImpl;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkTypeData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PhpInterpreterProviderImpl {

    public void registerInterpreter(@NotNull Project project) {
        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentRoots();
        VirtualFile ddevConfig = vFiles[0].findFileByRelativePath(".ddev/.ddev-docker-compose-full.yaml");

        if (ddevConfig == null || !ddevConfig.exists()) {
            return;
        }

        PhpInterpreter phpInterpreter = new PhpInterpreter();
        phpInterpreter.setName("DDEV");
        phpInterpreter.setIsProjectLevel(true);

        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData("php");
        sdkData.setInterpreterId(phpInterpreter.getId());

        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialsType.getInstance().createCredentials();
        credentials.setAccountName("Docker");
        credentials.setComposeFilePaths(List.of(ddevConfig.getPath()));
        credentials.setComposeServiceName("web");
        credentials.setRemoteProjectPath(DockerCredentialsEditor.DEFAULT_DOCKER_PROJECT_PATH);
        credentials.setEnvs(EnvironmentVariablesData.create(Map.of("COMPOSE_PROJECT_NAME", "ddev-config-test"), true));

        final PhpRemoteSdkTypeData typeData = sdkData.getTypeData();
        if (typeData instanceof PhpDockerComposeTypeData) {
            ((PhpDockerComposeTypeData) typeData).setCommand(PhpDockerComposeStartCommand.EXEC);
        }

        sdkData.setCredentials(DockerComposeCredentialsType.getInstance().getCredentialsKey(), credentials);

        final PathMappingSettings mappings = PhpRemoteInterpreterManager.getInstance().createPathMappings(project, sdkData);
        sdkData.setPathMappings(mappings);

        phpInterpreter.setPhpSdkAdditionalData(sdkData);

        this.addInterpreterConditionally(phpInterpreter, project);
    }

    private void addInterpreterConditionally(@NotNull PhpInterpreter newInterpreter, @NotNull Project project) {
        PhpInterpretersManagerImpl interpretersManager = PhpInterpretersManagerImpl.getInstance(project);


        PhpInterpreter interpreter = interpretersManager.findInterpreter(newInterpreter.getName());
        if (interpreter == null) {
            interpretersManager.addInterpreter(newInterpreter);
        } else {
            List<PhpInterpreter> interpreters = interpretersManager.getInterpreters();
            interpreters.remove(interpreter);
            interpreters.add(newInterpreter);
            interpretersManager.setInterpreters(interpreters);
        }
    }
}

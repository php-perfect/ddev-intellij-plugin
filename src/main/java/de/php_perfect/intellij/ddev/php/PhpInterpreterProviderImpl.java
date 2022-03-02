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
import com.jetbrains.php.config.interpreters.PhpInterpretersPhpInfoCacheImpl;
import com.jetbrains.php.config.phpInfo.PhpInfo;
import com.jetbrains.php.config.phpInfo.PhpInfoUtil;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeStartCommand;
import com.jetbrains.php.remote.docker.compose.PhpDockerComposeTypeData;
import com.jetbrains.php.remote.interpreter.PhpRemoteSdkAdditionalData;
import com.jetbrains.php.run.remote.PhpRemoteInterpreterManager;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;

public class PhpInterpreterProviderImpl {
    private final @NotNull Project project;

    public PhpInterpreterProviderImpl(@NotNull Project project) {
        this.project = project;
    }

    public void registerInterpreter(Description description) {
        VirtualFile[] vFiles = ProjectRootManager.getInstance(this.project).getContentRoots();
        VirtualFile ddevConfig = vFiles[0].findFileByRelativePath(".ddev/.ddev-docker-compose-full.yaml");

        if (ddevConfig == null || !ddevConfig.exists()) {
            return;
        }

        PhpInterpreter phpInterpreter = new PhpInterpreter();
        phpInterpreter.setName("DDEV");
        phpInterpreter.setIsProjectLevel(true);

        final PhpRemoteSdkAdditionalData sdkData = new PhpRemoteSdkAdditionalData("php8.1");
        sdkData.setInterpreterId(phpInterpreter.getId());
        sdkData.setHelpersPath("/opt/.phpstorm_helpers");

        final DockerComposeCredentialsHolder credentials = DockerComposeCredentialsType.getInstance().createCredentials();
        credentials.setAccountName("Docker");
        credentials.setComposeFilePaths(List.of(ddevConfig.getPath()));
        credentials.setComposeServiceName("web");
        credentials.setRemoteProjectPath(DockerCredentialsEditor.DEFAULT_DOCKER_PROJECT_PATH);
        credentials.setEnvs(EnvironmentVariablesData.create(Map.of("COMPOSE_PROJECT_NAME", "ddev-config-test"), true));

        sdkData.setTypeData(new PhpDockerComposeTypeData(PhpDockerComposeStartCommand.EXEC));
        sdkData.setCredentials(DockerComposeCredentialsType.getInstance().getCredentialsKey(), credentials);

        final PhpRemoteInterpreterManager phpRemoteInterpreterManager = PhpRemoteInterpreterManager.getInstance();
        if (phpRemoteInterpreterManager != null) {
            final PathMappingSettings mappings = phpRemoteInterpreterManager.createPathMappings(this.project, sdkData);
            sdkData.setPathMappings(mappings);
        }

        phpInterpreter.setPhpSdkAdditionalData(sdkData);

        PhpInfo phpInfo = PhpInfoUtil.getPhpInfo(this.project, phpInterpreter, null);
        PhpInterpretersPhpInfoCacheImpl.PhpProjectInterpretersPhpInfoCache.getInstance(this.project).setPhpInfo(phpInterpreter.getName(), phpInfo);

        this.addInterpreterConditionally(phpInterpreter);
    }

    private void addInterpreterConditionally(@NotNull PhpInterpreter newInterpreter) {
        PhpInterpretersManagerImpl interpretersManager = PhpInterpretersManagerImpl.getInstance(this.project);

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

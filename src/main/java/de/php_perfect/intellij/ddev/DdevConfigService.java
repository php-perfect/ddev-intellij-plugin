package de.php_perfect.intellij.ddev;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.VirtualFileManager;
import org.jetbrains.annotations.NotNull;

@Service(Service.Level.PROJECT)
public final class DdevConfigService implements Disposable {

    private final Project project;

    public DdevConfigService(@NotNull Project project) {
        this.project = project;
    }

    public void watchChanges() {
        VirtualFileManager virtualFileManager = VirtualFileManager.getInstance();
        VirtualFile[] vFiles = ProjectRootManager.getInstance(this.project).getContentRoots();
        // @todo: Wont work if file does not exist I guess
        VirtualFile ddevConfig = vFiles[0].findFileByRelativePath(".ddev/config.yaml");

        if (ddevConfig == null || !ddevConfig.exists()) {
            // todo: watch if file get created...
            return;
        }

        virtualFileManager.addAsyncFileListener(new FileChangeEventListener(ddevConfig.getPath()), this);
    }

    @Override
    public void dispose() {
    }
}

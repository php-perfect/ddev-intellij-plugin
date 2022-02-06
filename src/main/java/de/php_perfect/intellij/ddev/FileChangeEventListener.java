package de.php_perfect.intellij.ddev;

import com.intellij.openapi.vfs.AsyncFileListener;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.openapi.vfs.newvfs.events.VFileEvent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FileChangeEventListener implements AsyncFileListener {

    private final String ddevConfigPath;

    public FileChangeEventListener(String ddevConfigPath) {
        this.ddevConfigPath = ddevConfigPath;
    }

    @Override
    public @Nullable ChangeApplier prepareChange(@NotNull List<? extends @NotNull VFileEvent> events) {

        for (VFileEvent event : events) {
            VirtualFile file = event.getFile();

            if (file == null) {
                return null;
            }

            if (file.getPath().equals(this.ddevConfigPath) && file.exists()) {
                // .ddev/config.yaml
                System.out.println("DDev Config changed");
                System.out.println();
            }
        }

        return null;
    }
}

package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface StateWatcher {
    void startWatching();

    void stopWatching();

    boolean isWatching();

    static StateWatcher getInstance(@NotNull Project project) {
        return project.getService(StateWatcher.class);
    }
}

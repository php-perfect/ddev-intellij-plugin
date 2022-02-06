package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;

public interface DdevRunner {

    void runDdev(String title, String ddevAction);

    static DdevRunner getInstance(@NotNull Project project) {
        return project.getService(DdevRunner.class);
    }
}

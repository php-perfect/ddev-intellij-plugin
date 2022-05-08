package de.php_perfect.intellij.ddev.tutorial;

import com.intellij.openapi.Disposable;
import com.intellij.openapi.application.ApplicationManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public interface GotItTutorial {
    void showStatusBarTutorial(@NotNull JComponent component, @NotNull Disposable disposable);

    static GotItTutorial getInstance() {
        return ApplicationManager.getApplication().getService(GotItTutorial.class);
    }
}

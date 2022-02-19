package de.php_perfect.intellij.ddev.actions;

import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import org.jetbrains.annotations.NotNull;

import java.net.MalformedURLException;
import java.net.URL;

public class OpenServiceAction extends AnAction {
    public OpenServiceAction() {
        super("Test Action");
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        try {
            BrowserUtil.browse(new URL("https", "www.google.com", "/"));
        } catch (MalformedURLException ignored) {
        }
    }
}

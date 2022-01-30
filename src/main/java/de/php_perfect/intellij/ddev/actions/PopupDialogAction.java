package de.php_perfect.intellij.ddev.actions;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ScriptRunnerUtil;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.util.NlsActions;
import com.intellij.openapi.wm.RegisterToolWindowTask;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import icons.DdevIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PopupDialogAction extends AnAction {
    private JTextArea textArea;
    private ToolWindow toolWindow;

    private ExecutorService executor = Executors.newSingleThreadExecutor();

    public PopupDialogAction(@NotNull @NlsActions.ActionText String text) {
        super(text, null, DdevIcons.Sdk_default_icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            Logger.getGlobal().warning("No project found");
            return;
        }


        if (toolWindow == null) {
            textArea = new JTextArea();
            textArea.setEditable(false);

            toolWindow = ToolWindowManager.getInstance(Objects.requireNonNull(e.getProject()))
                    .registerToolWindow(new RegisterToolWindowTask("Ddev-Log", ToolWindowAnchor.BOTTOM, textArea, true,
                            false, false, true, null, null,
                            null));
        }

        toolWindow.show();
        toolWindow.setTitle("Ddev-Log");
        toolWindow.setToHideOnEmptyContent(false);
        toolWindow.setAutoHide(false);
        toolWindow.activate(null);
        executor.execute(() -> {
            textArea.setText("");

            try {
                GeneralCommandLine commandLine = new GeneralCommandLine("echo", e.getPresentation().getText());
                commandLine.setCharset(StandardCharsets.UTF_8);
                commandLine.setWorkDirectory(e.getProject().getBasePath());

                textArea.append(ScriptRunnerUtil.getProcessOutput(commandLine) + "\n");
            } catch (ExecutionException ex) {
                Logger.getGlobal().log(Level.FINEST, "An error occurred", ex);
                textArea.append(ex.getMessage() + "\n");
            }
        });
    }
}

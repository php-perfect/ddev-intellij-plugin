package de.php_perfect.intellij.ddev;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.wm.RegisterToolWindowTask;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DdevExecutor {
    private static final Map<Project, DdevExecutor> instances = new WeakHashMap<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Project project;
    private final JTextArea textArea = new JTextArea();
    private final ToolWindow toolWindow;

    public DdevExecutor(@NotNull Project project) {
        this.project = project;

        textArea.setEditable(false);

        toolWindow = ToolWindowManager.getInstance(Objects.requireNonNull(project))
                .registerToolWindow(new RegisterToolWindowTask("Ddev", ToolWindowAnchor.BOTTOM, textArea, true,
                        false, false, true, null, null,
                        null));
        toolWindow.setTitle("Log");
        toolWindow.setToHideOnEmptyContent(false);
        toolWindow.setAutoHide(false);
    }

    public void runDdev(String... args) {
        executor.execute(() -> {
            try {
                SwingUtilities.invokeLater(toolWindow::show);
                textArea.setText("");

                List<String> command = new ArrayList<>(args.length + 1);
                command.add("ddev");
                Collections.addAll(command, args);

                GeneralCommandLine commandLine = new GeneralCommandLine(command);
                commandLine.setCharset(StandardCharsets.UTF_8);
                commandLine.setWorkDirectory(this.project.getBasePath());

                OSProcessHandler processHandler = new OSProcessHandler(commandLine);
                processHandler.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void onTextAvailable(@NotNull ProcessEvent e, @NotNull Key outputType) {
                        textArea.append(e.getText());
                    }

                    @Override
                    public void processTerminated(@NotNull ProcessEvent e) {
                        textArea.append("Terminated with exit code " + e.getExitCode() + "\n");
                    }
                });
                processHandler.startNotify();
            } catch (Throwable th) {
                Logger.getGlobal().log(Level.FINEST, "An error occurred", th);
                textArea.append(th.getMessage() + "\n");
            }
        });
    }

    public static DdevExecutor getInstance(Project project) {
        instances.computeIfAbsent(project, DdevExecutor::new);

        return instances.get(project);
    }
}

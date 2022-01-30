package de.php_perfect.intellij.ddev;

import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.filters.TextConsoleBuilderFactory;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.execution.ui.ConsoleView;
import com.intellij.execution.ui.ConsoleViewContentType;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.RegisterToolWindowTask;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowAnchor;
import com.intellij.openapi.wm.ToolWindowManager;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
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
    private final ConsoleView c;
    private final ToolWindow toolWindow;

    public DdevExecutor(@NotNull Project project) {
        this.project = project;

        c = TextConsoleBuilderFactory.getInstance().createBuilder(project).getConsole();

        toolWindow = ToolWindowManager.getInstance(Objects.requireNonNull(project))
                .registerToolWindow(new RegisterToolWindowTask("ddev", ToolWindowAnchor.BOTTOM, c.getComponent(), true,
                        false, false, true, null, null,
                        null));
        toolWindow.setTitle("Log");
        toolWindow.setToHideOnEmptyContent(false);
        toolWindow.setAutoHide(false);
    }

    public void runDdev(String title, String ddevAction) {
        executor.execute(() -> {
            try {
                SwingUtilities.invokeLater(() -> {
                    toolWindow.setTitle(title);
                    toolWindow.show();
                });
                c.clear();

                GeneralCommandLine commandLine = new GeneralCommandLine("ddev", ddevAction);
                commandLine.setCharset(StandardCharsets.UTF_8);
                commandLine.setWorkDirectory(this.project.getBasePath());

                OSProcessHandler processHandler = new OSProcessHandler(commandLine);
                c.attachToProcess(processHandler);
                processHandler.addProcessListener(new ProcessAdapter() {
                    @Override
                    public void processTerminated(@NotNull ProcessEvent e) {
                        if (e.getExitCode() != 0) {
                            c.print("\n\nProcess exited with code " + e.getExitCode(), ConsoleViewContentType.ERROR_OUTPUT);
                        }
                    }
                });
                processHandler.startNotify();
            } catch (Throwable th) {
                Logger.getGlobal().log(Level.FINEST, "An error occurred", th);
                c.print("\n\nAn error occurred: " + th.getMessage(), ConsoleViewContentType.ERROR_OUTPUT);
            }
        });
    }

    public static DdevExecutor getInstance(Project project) {
        instances.computeIfAbsent(project, DdevExecutor::new);

        return instances.get(project);
    }
}

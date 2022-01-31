package de.php_perfect.intellij.ddev;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.RunContentExecutor;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ColoredProcessHandler;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.execution.process.ProcessTerminatedListener;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DdevExecutor {
    private static final Map<Project, DdevExecutor> instances = new WeakHashMap<>();

    private final ExecutorService executor = Executors.newSingleThreadExecutor();
    private final Project project;

    public DdevExecutor(@NotNull Project project) {
        this.project = project;
    }

    public void runDdev(String title, String ddevAction) {
        String runTitle = "DDev " + title;
        executor.execute(() -> SwingUtilities.invokeLater(() -> {
            try {
                this.run(runTitle, ddevAction);
            } catch (Throwable th) {
                Logger.getGlobal().log(Level.FINEST, "An error occurred", th);
            }
        }));
    }

    public void run(String title, String action) throws ExecutionException {
        final ProcessHandler process = this.createProcessHandler(action);
        final RunContentExecutor runContentExecutor = new RunContentExecutor(this.project, process)
                .withTitle(title)
                .withFocusToolWindow(true)
                .withActivateToolWindow(true)
                .withStop(process::destroyProcess, () -> !process.isProcessTerminated());
        Disposer.register(this.project, runContentExecutor);
        runContentExecutor.run();
    }

    @NotNull
    public ProcessHandler createProcessHandler(String ddevAction) throws ExecutionException {
        final ProcessHandler handler = new ColoredProcessHandler(this.createCommandLine(ddevAction));
        ProcessTerminatedListener.attach(handler);
        return handler;
    }

    @NotNull
    private GeneralCommandLine createCommandLine(String ddevAction) {
        final GeneralCommandLine commandLine = new GeneralCommandLine("ddev", ddevAction);
        commandLine.setCharset(StandardCharsets.UTF_8);
        commandLine.setWorkDirectory(this.project.getBasePath());
        return commandLine;
    }

    @NotNull
    public static DdevExecutor getInstance(Project project) {
        return instances.computeIfAbsent(project, DdevExecutor::new);
    }
}

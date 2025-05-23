package de.php_perfect.intellij.ddev.php.composer;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessHandler;
import com.intellij.openapi.project.Project;
import com.jetbrains.php.composer.execution.ComposerExecution;
import com.jetbrains.php.composer.statistics.ComposerExecutorStatProvider;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.State;
import org.jdom.Element;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

/**
 * DDEV-specific implementation of ComposerExecution that runs Composer commands through DDEV.
 */
public class DdevComposerExecution implements ComposerExecution, ComposerExecutorStatProvider {
    private static final String ROOT_TAG_NAME = "ddev";

    public DdevComposerExecution() {
        // No configuration needed for DDEV composer
    }

    @Override
    @NotNull
    public ProcessHandler createProcessHandler(@NotNull Project project, String workingDir, @NotNull List<String> command, @NotNull String commandText) throws ExecutionException {
        // Build the DDEV command: ddev composer [args...]
        GeneralCommandLine commandLine = new GeneralCommandLine();
        commandLine.setExePath("ddev");
        commandLine.addParameter("composer");
        commandLine.addParameters(command);
        commandLine.setWorkDirectory(workingDir != null ? workingDir : project.getBasePath());

        try {
            return new DdevComposerProcessHandler(commandLine.createProcess(), commandText);
        } catch (Exception e) {
            throw new ExecutionException("Failed to start DDEV Composer process: " + e.getMessage(), e);
        }
    }

    @Override
    public void cancelProcess(@NotNull ProcessHandler handler) {
        handler.destroyProcess();
    }

    @Override
    public boolean isWellConfigured(@NotNull Project project, boolean checkComposer) {
        return getValidationMessage(project) == null;
    }

    @Nullable
    public String getValidationMessage(@NotNull Project project) {
        // Check if DDEV is available and project is configured
        try {
            State state = DdevStateManager.getInstance(project).getState();
            if (!state.isBinaryConfigured()) {
                return "DDEV binary is not configured";
            }

            // Try to get version to check if DDEV is working
            Ddev.getInstance().version(state.getDdevBinary(), project);

            // Check if project is configured
            if (!state.isConfigured()) {
                return "DDEV project is not configured";
            }

            return null;
        } catch (CommandFailedException e) {
            return "DDEV is not available or not properly configured: " + e.getMessage();
        } catch (Exception e) {
            return "Failed to validate DDEV configuration: " + e.getMessage();
        }
    }

    // ComposerExecutorStatProvider methods
    @Override
    public boolean isConfigured() {
        return true; // DDEV composer is always configured if DDEV is available
    }

    @Override
    public boolean isDefault() {
        return false; // DDEV is not the default composer execution
    }

    @Nullable
    public String getRemoteInterpreterType(@NotNull Project project) {
        return "ddev";
    }

    @NotNull
    public Element save() {
        Element element = new Element(ROOT_TAG_NAME);
        // No attributes needed for DDEV composer
        return element;
    }

    @Nullable
    public static DdevComposerExecution load(@NotNull Element element) {
        if (!ROOT_TAG_NAME.equals(element.getName())) {
            return null;
        }

        return new DdevComposerExecution();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        return o != null && getClass() == o.getClass();
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "DdevComposerExecution{}";
    }
}

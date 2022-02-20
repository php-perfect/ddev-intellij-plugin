package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParser;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParserException;
import de.php_perfect.intellij.ddev.serviceActions.ServiceActionManagerImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public final class DdevImpl implements Ddev {
    private final @NotNull Project project;
    private static final @NotNull Logger LOGGER = Logger.getInstance(ServiceActionManagerImpl.class.getName());

    public DdevImpl(@NotNull Project project) {
        this.project = project;
    }

    public @NotNull Versions version() throws CommandFailedException {
        return execute("version", Versions.class);
    }

    public @NotNull Description describe() throws CommandFailedException {
        return execute("describe", Description.class);
    }

    private @NotNull <T> T execute(String action, Type type) throws CommandFailedException {
        final GeneralCommandLine commandLine = createDdevCommandLine(action);

        try {
            try {
                final ProcessOutput processOutput = ApplicationManager.getApplication().getService(ProcessExecutor.class).executeCommandLine(commandLine);

                if (processOutput.getExitCode() != 0) {
                    throw new CommandFailedException("Command returned non zero exit code " + commandLine.getCommandLineString());
                }

                return JsonParser.getInstance().parse(processOutput.getStdout(), type);
            } catch (ExecutionException exception) {
                throw new CommandFailedException("Failed to execute " + commandLine.getCommandLineString(), exception);
            } catch (JsonParserException exception) {
                throw new CommandFailedException("Failed to parse output of command " + commandLine.getCommandLineString(), exception);
            }
        } catch (Exception exception) {
            LOGGER.warn(exception);
            throw exception;
        }
    }

    private @NotNull WslAwareCommandLine createDdevCommandLine(String action) {
        //return (WslAwareCommandLine) new WslAwareCommandLine(project.getBasePath(), "echo", "{]").withEnvironment("DDEV_NONINTERACTIVE", "true");
        return (WslAwareCommandLine) new WslAwareCommandLine(project.getBasePath(), "ddev", action, "--json-output").withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}

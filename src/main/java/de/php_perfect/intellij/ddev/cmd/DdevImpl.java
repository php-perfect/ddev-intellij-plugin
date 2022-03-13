package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParser;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParserException;
import de.php_perfect.intellij.ddev.serviceActions.ServiceActionManagerImpl;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public final class DdevImpl implements Ddev {
    private static final @NotNull Logger LOG = Logger.getInstance(ServiceActionManagerImpl.class.getName());
    private static final @NotNull String DDEV_COMMAND = "ddev";

    @Override
    public boolean isInstalled(@NotNull Project project) throws CommandFailedException {
        final String projectDir = project.getBasePath();
        final GeneralCommandLine commandLine = new GeneralCommandLine(WhichProvider.getWhichCommand(projectDir), DDEV_COMMAND).withWorkDirectory(projectDir);

        try {
            final ProcessOutput processOutput = ProcessExecutor.getInstance().executeCommandLine(commandLine, 5_000);
            return processOutput.getExitCode() == 0;
        } catch (ExecutionException e) {
            LOG.error(e);
            return false;
        }
    }

    public @NotNull Versions version(@NotNull Project project) throws CommandFailedException {
        return execute("version", Versions.class, project);
    }

    public @NotNull Description describe(@NotNull Project project) throws CommandFailedException {
        return execute("describe", Description.class, project);
    }

    private @NotNull <T> T execute(String action, Type type, @NotNull Project project) throws CommandFailedException {
        final GeneralCommandLine commandLine = createDdevCommandLine(action, project.getBasePath());

        try {
            try {
                final ProcessOutput processOutput = ProcessExecutor.getInstance().executeCommandLine(commandLine, 5_000);

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
            LOG.error(exception);
            throw exception;
        }
    }

    private @NotNull GeneralCommandLine createDdevCommandLine(String action, String workingDirectory) {
        return new GeneralCommandLine(DDEV_COMMAND, action, "--json-output").withWorkDirectory(workingDirectory).withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}

package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParser;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParserException;
import de.php_perfect.intellij.ddev.version.Version;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class DdevImpl implements Ddev {
    private static final int COMMAND_TIMEOUT = 8_000;

    @Override
    public @NotNull Version version(@NotNull String binary, @NotNull Project project) throws CommandFailedException {
        final String versionString = this.executePlain(binary, "--version", project);
        final Pattern r = Pattern.compile("ddev version (v.*)$");
        final Matcher m = r.matcher(versionString);

        if (m.find()) {
            return new Version(m.group(1));
        }

        throw new CommandFailedException("Unexpcted output of ddev version command: " + versionString);
    }

    public @NotNull Versions detailedVersions(final @NotNull String binary, final @NotNull Project project) throws CommandFailedException {
        return execute(binary, "version", Versions.class, project);
    }

    public @NotNull Description describe(final @NotNull String binary, final @NotNull Project project) throws CommandFailedException {
        return execute(binary, "describe", Description.class, project);
    }

    private @NotNull String executePlain(final @NotNull String binary, final @NotNull String action, final @NotNull Project project) throws CommandFailedException {
        final GeneralCommandLine commandLine = createDdevCommandLine(binary, action, project);

        try {
            final ProcessOutput processOutput = ProcessExecutor.getInstance().executeCommandLine(commandLine, COMMAND_TIMEOUT, false);

            if (processOutput.isTimeout()) {
                throw new CommandFailedException("Command timed out after " + (COMMAND_TIMEOUT / 1000) + " seconds: " + commandLine.getCommandLineString() + " in " + commandLine.getWorkDirectory().getPath());
            }

            if (processOutput.getExitCode() != 0) {
                throw new CommandFailedException("Command '" + commandLine.getCommandLineString() + "' returned non zero exit code " + processOutput);
            }

            return processOutput.getStdout();
        } catch (ExecutionException exception) {
            throw new CommandFailedException("Failed to execute " + commandLine.getCommandLineString(), exception);
        }
    }

    private @NotNull <T> T execute(final @NotNull String binary, final @NotNull String action, final @NotNull Type type, final @NotNull Project project) throws CommandFailedException {
        final GeneralCommandLine commandLine = createDdevCommandLine(binary, action, project);

        ProcessOutput processOutput = null;
        try {
            processOutput = ProcessExecutor.getInstance().executeCommandLine(commandLine, COMMAND_TIMEOUT, false);

            if (processOutput.isTimeout()) {
                throw new CommandFailedException("Command timed out after " + (COMMAND_TIMEOUT / 1000) + " seconds: " + commandLine.getCommandLineString() + " in " + commandLine.getWorkDirectory().getPath());
            }

            if (processOutput.getExitCode() != 0) {
                throw new CommandFailedException("Command '" + commandLine.getCommandLineString() + "' returned non zero exit code " + processOutput);
            }

            return JsonParser.getInstance().parse(processOutput.getStdout(), type);
        } catch (ExecutionException exception) {
            throw new CommandFailedException("Failed to execute " + commandLine.getCommandLineString(), exception);
        } catch (JsonParserException exception) {
            throw new CommandFailedException("Failed to parse output of command '" + commandLine.getCommandLineString() + "': " + processOutput.getStdout(), exception);
        }
    }

    private @NotNull GeneralCommandLine createDdevCommandLine(final @NotNull String binary, final @NotNull String action, final @NotNull Project project) {
        return new GeneralCommandLine(Objects.requireNonNull(binary), action, "--json-output")
                .withWorkDirectory(project.getBasePath())
                .withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}

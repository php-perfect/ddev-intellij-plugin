package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public final class DdevImpl implements Ddev {
    private final @NotNull Project project;

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
        final WslAwareCommandLine commandLine = createDdevCommandLine(action);

        final Process process;

        try {
            process = commandLine.createProcess();
        } catch (ExecutionException exception) {
            throw new CommandFailedException("Command not found", exception);
        }

        Future<String> output = ApplicationManager.getApplication().executeOnPooledThread(() -> readProcessOutput(process));

        try {
            if (!process.waitFor(5L, TimeUnit.SECONDS)) {
                process.destroyForcibly();
                throw new CommandFailedException("Command time out " + commandLine.getCommandLineString());
            }

            if (process.exitValue() != 0) {
                throw new CommandFailedException("Command execution failed " + commandLine.getCommandLineString());
            }
        } catch (InterruptedException ignored) {
        }

        try {
            return JsonParser.getInstance().parse(output.get(1L, TimeUnit.SECONDS), type);
        } catch (InterruptedException e) {
            throw new CommandFailedException("Execution was interrupted " + commandLine.getCommandLineString());
        } catch (java.util.concurrent.ExecutionException e) {
            throw new CommandFailedException("Execution failed " + commandLine.getCommandLineString());
        } catch (TimeoutException e) {
            throw new CommandFailedException("Command time out " + commandLine.getCommandLineString());
        }
    }

    private @NotNull String readProcessOutput(Process process) {
        final StringBuilder out = new StringBuilder();
        final int bufferSize = 1024;
        final char[] buffer = new char[bufferSize];

        try (InputStreamReader reader = new InputStreamReader(process.getInputStream(), StandardCharsets.UTF_8)) {
            for (int numRead; (numRead = reader.read(buffer, 0, buffer.length)) > 0; ) {
                out.append(buffer, 0, numRead);
            }
        } catch (IOException ignored) {
        }

        return out.toString();
    }

    @NotNull
    private WslAwareCommandLine createDdevCommandLine(String action) {
        return (WslAwareCommandLine) new WslAwareCommandLine(project.getBasePath(), "ddev", action, "--json-output").withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}

package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.cmd.parser.JsonParser;
import org.jetbrains.annotations.NotNull;

import java.io.InputStreamReader;
import java.lang.reflect.Type;

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

        try {
            final Process process = commandLine.createProcess();

            return JsonParser.getInstance().parse(new InputStreamReader(process.getInputStream()), type);
        } catch (ExecutionException exception) {
            throw new CommandFailedException("Command not found " + commandLine.getCommandLineString(), exception);
        }
    }

    @NotNull
    private WslAwareCommandLine createDdevCommandLine(String action) {
        return (WslAwareCommandLine) new WslAwareCommandLine(project.getBasePath(), "ddev", action, "--json-output").withEnvironment("DDEV_NONINTERACTIVE", "true");
    }
}

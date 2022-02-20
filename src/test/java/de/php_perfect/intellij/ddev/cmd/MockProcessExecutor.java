package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.ProcessOutput;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class MockProcessExecutor implements ProcessExecutor {
    private final Map<String, ProcessOutput> processList = new HashMap<>();

    public void addProcessOutput(@NotNull String command, @NotNull ProcessOutput processOutput) {
        this.processList.put(command, processOutput);
    }

    @Override
    public @NotNull ProcessOutput executeCommandLine(GeneralCommandLine commandLine) throws ExecutionException {
        String commandLineString = commandLine.getCommandLineString();

        if (!this.processList.containsKey(commandLineString)) {
            throw new ExecutionException(String.format("Command '%s' was not expected", commandLineString));
        }

        return this.processList.get(commandLineString);
    }
}

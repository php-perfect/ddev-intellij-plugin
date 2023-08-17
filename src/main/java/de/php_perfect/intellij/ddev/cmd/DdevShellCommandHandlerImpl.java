package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.Executor;
import com.intellij.openapi.project.Project;
import com.intellij.terminal.TerminalShellCommandHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public final class DdevShellCommandHandlerImpl implements TerminalShellCommandHandler {
    static final @NotNull String PREFIX = "ddev ";

    enum Action {
        START,
        STOP,
        RESTART,
        POWER_OFF,
        DELETE,
        SHARE,
        CONFIG,
    }

    @Override
    public boolean execute(@NotNull Project project, @Nullable String workingDirectory, boolean localSession, @NotNull String command, @NotNull Executor executor) {
        final Action action = parseAction(command);

        if (action == null) {
            return false;
        }

        this.executeAction(action, project);

        return true;
    }

    @Override
    public boolean matches(@NotNull Project project, @Nullable String workingDirectory, boolean localSession, @NotNull String command) {
        return parseAction(command) != null;
    }

    private Action parseAction(@NotNull String command) {
        if (!command.startsWith(PREFIX)) {
            return null;
        }

        final String actionString = command.substring(PREFIX.length()).trim();

        return this.matchAction(actionString);
    }

    private Action matchAction(@NotNull String action) {
        return switch (action) {
            case "start" -> Action.START;
            case "stop" -> Action.STOP;
            case "restart" -> Action.RESTART;
            case "poweroff" -> Action.POWER_OFF;
            case "delete" -> Action.DELETE;
            case "share" -> Action.SHARE;
            case "config" -> Action.CONFIG;
            default -> null;
        };
    }

    private void executeAction(@NotNull Action action, @NotNull Project project) {
        final DdevRunner ddevRunner = DdevRunner.getInstance();

        switch (action) {
            case START -> ddevRunner.start(project);
            case STOP -> ddevRunner.stop(project);
            case RESTART -> ddevRunner.restart(project);
            case POWER_OFF -> ddevRunner.powerOff(project);
            case DELETE -> ddevRunner.delete(project);
            case SHARE -> ddevRunner.share(project);
            case CONFIG -> ddevRunner.config(project);
        }
    }
}

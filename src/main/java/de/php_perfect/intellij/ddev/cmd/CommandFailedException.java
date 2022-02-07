package de.php_perfect.intellij.ddev.cmd;

public class CommandFailedException extends Exception {
    public CommandFailedException(String message) {
        super(message);
    }

    public CommandFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}

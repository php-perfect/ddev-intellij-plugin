package de.php_perfect.intellij.ddev.cmd;

public class DdevCmdException extends Exception {
    public DdevCmdException(String message) {
        super(message);
    }

    public DdevCmdException(String message, Throwable cause) {
        super(message, cause);
    }
}

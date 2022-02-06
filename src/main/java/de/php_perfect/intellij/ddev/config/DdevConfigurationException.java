package de.php_perfect.intellij.ddev.config;

public class DdevConfigurationException extends Exception {
    public DdevConfigurationException(String message) {
        super(message);
    }

    public DdevConfigurationException(String message, Throwable cause) {
        super(message, cause);
    }
}

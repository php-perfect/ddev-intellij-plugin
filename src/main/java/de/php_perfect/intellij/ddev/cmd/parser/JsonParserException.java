package de.php_perfect.intellij.ddev.cmd.parser;

import org.jetbrains.annotations.NotNull;

public class JsonParserException extends RuntimeException {
    public JsonParserException(String message) {
        super(message);
    }

    public JsonParserException(String message, @NotNull Throwable cause) {
        super(message, cause);
    }
}

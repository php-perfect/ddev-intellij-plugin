package de.php_perfect.intellij.ddev.cmd.parser;

import org.jetbrains.annotations.Nullable;

public class LogLine<T> {

    private final @Nullable T raw;

    public LogLine(@Nullable T raw) {
        this.raw = raw;
    }

    public @Nullable T getRaw() {
        return this.raw;
    }
}

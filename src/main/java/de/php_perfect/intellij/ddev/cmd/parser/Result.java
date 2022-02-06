package de.php_perfect.intellij.ddev.cmd.parser;

import org.jetbrains.annotations.Nullable;

public class Result<T> {

    private final @Nullable T raw;

    public Result(@Nullable T raw) {
        this.raw = raw;
    }

    public @Nullable T getRaw() {
        return this.raw;
    }
}

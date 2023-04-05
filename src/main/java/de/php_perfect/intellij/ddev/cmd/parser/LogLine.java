package de.php_perfect.intellij.ddev.cmd.parser;

import org.jetbrains.annotations.Nullable;

public record LogLine<T>(@Nullable T raw) {

}

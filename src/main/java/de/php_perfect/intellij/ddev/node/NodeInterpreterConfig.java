package de.php_perfect.intellij.ddev.node;

import de.php_perfect.intellij.ddev.index.IndexableConfiguration;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public record NodeInterpreterConfig(@NotNull String name, @NotNull String composeFilePath,
                                    @NotNull String binaryPath) implements IndexableConfiguration {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NodeInterpreterConfig that = (NodeInterpreterConfig) o;
        return Objects.equals(name, that.name) && Objects.equals(composeFilePath, that.composeFilePath) && Objects.equals(binaryPath, that.binaryPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, composeFilePath, binaryPath);
    }

    @Override
    public String toString() {
        return "NodeInterpreterConfig{" +
                "name='" + name + '\'' +
                ", composeFilePath='" + composeFilePath + '\'' +
                ", binaryPath='" + binaryPath + '\'' +
                '}';
    }
}

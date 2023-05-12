package de.php_perfect.intellij.ddev.php;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

record DdevInterpreterConfig(@NotNull String name, @NotNull String phpVersion, @NotNull String composeFilePath) {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DdevInterpreterConfig that = (DdevInterpreterConfig) o;
        return name().equals(that.name()) && phpVersion().equals(that.phpVersion()) && composeFilePath().equals(that.composeFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name(), phpVersion(), composeFilePath());
    }

    @Override
    public String toString() {
        return "DdevInterpreterConfig{" +
                "name='" + name + '\'' +
                ", phpVersion='" + phpVersion + '\'' +
                ", composeFilePath='" + composeFilePath + '\'' +
                '}';
    }
}

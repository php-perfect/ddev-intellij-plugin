package de.php_perfect.intellij.ddev.php;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

class DdevInterpreterConfig {
    private final @NotNull String name;
    private final @NotNull String phpVersion;
    private final @NotNull String composeFilePath;

    public DdevInterpreterConfig(@NotNull String name, @NotNull String phpVersion, @NotNull String composeFilePath) {
        this.name = name;
        this.phpVersion = phpVersion;
        this.composeFilePath = composeFilePath;
    }

    public @NotNull String getName() {
        return name;
    }

    public @NotNull String getPhpVersion() {
        return phpVersion;
    }

    public @NotNull String getComposeFilePath() {
        return composeFilePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DdevInterpreterConfig that = (DdevInterpreterConfig) o;
        return getName().equals(that.getName()) && getPhpVersion().equals(that.getPhpVersion()) && getComposeFilePath().equals(that.getComposeFilePath());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getPhpVersion(), getComposeFilePath());
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

package de.php_perfect.intellij.ddev.version;

import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public final class LatestRelease {
    private final @Nullable String tagName;

    public LatestRelease(@Nullable String tagName) {
        this.tagName = tagName;
    }

    public @Nullable String getTagName() {
        return tagName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LatestRelease that = (LatestRelease) o;
        return Objects.equals(getTagName(), that.getTagName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTagName());
    }

    @Override
    public String toString() {
        return "LatestRelease{" +
                "tagName='" + tagName + '\'' +
                '}';
    }
}

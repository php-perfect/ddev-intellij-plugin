package de.php_perfect.intellij.ddev.version;

import org.jetbrains.annotations.NotNull;

public final class LatestRelease {

    private final @NotNull String tagName;

    public LatestRelease(@NotNull String tagName) {
        this.tagName = tagName;
    }

    public @NotNull String getTagName() {
        return tagName;
    }
}

package de.php_perfect.intellij.ddev.index;

import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record IndexEntry(@NonNls @NotNull String id, @NonNls @Nullable String hash) {
    public boolean hashEquals(int hash) {
        return this.hashEquals(Integer.toHexString(hash));
    }

    public boolean hashEquals(@Nullable String hash) {
        return Objects.equals(hash(), hash);
    }
}

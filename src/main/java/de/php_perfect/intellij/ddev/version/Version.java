package de.php_perfect.intellij.ddev.version;

import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public final class Version implements Comparable<Version> {

    public final int @NotNull [] numbers;


    public final @NotNull String string;

    public Version(@NotNull String version) {
        this.string = version;
        final String[] split = version.replaceAll("^v", "").split("-")[0].split("\\.");
        numbers = new int[split.length];
        for (int i = 0; i < split.length; i++) {
            numbers[i] = Integer.parseInt(split[i]);
        }
    }

    @Override
    public int compareTo(@NotNull Version another) {
        final int maxLength = Math.max(numbers.length, another.numbers.length);
        for (int i = 0; i < maxLength; i++) {
            final int left = i < numbers.length ? numbers[i] : 0;
            final int right = i < another.numbers.length ? another.numbers[i] : 0;
            if (left != right) {
                return left < right ? -1 : 1;
            }
        }
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Version version = (Version) o;
        return Arrays.equals(numbers, version.numbers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(numbers);
    }

    @Override
    public String toString() {
        return this.string;
    }
}

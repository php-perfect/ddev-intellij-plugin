package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Versions {
    @SerializedName("DDEV version")
    private final @Nullable String ddevVersion;

    public @Nullable String getDdevVersion() {
        return ddevVersion;
    }

    public Versions(@Nullable String ddevVersion) {
        this.ddevVersion = ddevVersion;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Versions)) return false;
        Versions versions = (Versions) o;
        return Objects.equals(getDdevVersion(), versions.getDdevVersion());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDdevVersion());
    }

    @Override
    public String toString() {
        return "Versions{" +
                "ddevVersion='" + ddevVersion + '\'' +
                '}';
    }
}

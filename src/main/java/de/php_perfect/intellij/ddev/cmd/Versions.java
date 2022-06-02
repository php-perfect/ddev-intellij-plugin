package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class Versions {
    @SerializedName("DDEV version")
    private final @Nullable String ddevVersion;

    @SerializedName("docker")
    private final @Nullable String dockerVersion;

    @SerializedName("docker-compose")
    private final @Nullable String dockerComposeVersion;

    @SerializedName("docker-platform")
    private final @Nullable String dockerPlatform;

    public Versions(@Nullable String ddevVersion, @Nullable String dockerVersion, @Nullable String dockerComposeVersion, @Nullable String dockerPlatform) {
        this.ddevVersion = ddevVersion;
        this.dockerVersion = dockerVersion;
        this.dockerComposeVersion = dockerComposeVersion;
        this.dockerPlatform = dockerPlatform;
    }

    public @Nullable String getDdevVersion() {
        return ddevVersion;
    }

    public @Nullable String getDockerVersion() {
        return dockerVersion;
    }

    public @Nullable String getDockerComposeVersion() {
        return dockerComposeVersion;
    }

    public @Nullable String getDockerPlatform() {
        return dockerPlatform;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Versions versions = (Versions) o;
        return Objects.equals(getDdevVersion(), versions.getDdevVersion()) && Objects.equals(getDockerVersion(), versions.getDockerVersion()) && Objects.equals(getDockerComposeVersion(), versions.getDockerComposeVersion()) && Objects.equals(getDockerPlatform(), versions.getDockerPlatform());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getDdevVersion(), getDockerVersion(), getDockerComposeVersion(), getDockerPlatform());
    }

    @Override
    public String toString() {
        return "Versions{" +
                "ddevVersion='" + ddevVersion + '\'' +
                ", dockerVersion='" + dockerVersion + '\'' +
                ", dockerComposeVersion='" + dockerComposeVersion + '\'' +
                ", dockerPlatform='" + dockerPlatform + '\'' +
                '}';
    }
}

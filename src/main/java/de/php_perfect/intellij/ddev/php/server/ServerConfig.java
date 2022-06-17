package de.php_perfect.intellij.ddev.php.server;

import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

public final class ServerConfig {
    private final @NotNull String localPath;

    private final @NotNull String remotePath;

    private final @NotNull URI url;

    public ServerConfig(@NotNull String localPath, @NotNull String remotePathPath, @NotNull URI url) {
        this.localPath = localPath;
        this.remotePath = remotePathPath;
        this.url = url;
    }

    public @NotNull String getLocalPath() {
        return localPath;
    }

    public @NotNull String getRemotePath() {
        return remotePath;
    }

    public @NotNull URI getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfig that = (ServerConfig) o;
        return getLocalPath().equals(that.getLocalPath()) && getRemotePath().equals(that.getRemotePath()) && getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocalPath(), getRemotePath(), getUrl());
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "localPath='" + localPath + '\'' +
                ", remotePath='" + remotePath + '\'' +
                ", url=" + url +
                '}';
    }
}

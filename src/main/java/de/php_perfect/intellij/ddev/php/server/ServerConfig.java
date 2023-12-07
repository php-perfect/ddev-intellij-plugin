package de.php_perfect.intellij.ddev.php.server;

import de.php_perfect.intellij.ddev.index.IndexableConfiguration;
import org.jetbrains.annotations.NotNull;

import java.net.URI;
import java.util.Objects;

public record ServerConfig(@NotNull String localPath, @NotNull String remotePathPath,
                           @NotNull URI url) implements IndexableConfiguration {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ServerConfig that = (ServerConfig) o;
        return Objects.equals(localPath, that.localPath) && Objects.equals(remotePathPath, that.remotePathPath) && Objects.equals(url, that.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(localPath, remotePathPath, url);
    }

    @Override
    public String toString() {
        return "ServerConfig{" +
                "localPath='" + localPath + '\'' +
                ", remotePathPath='" + remotePathPath + '\'' +
                ", url=" + url +
                '}';
    }
}

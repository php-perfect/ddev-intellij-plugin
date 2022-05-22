package de.php_perfect.intellij.ddev.deployment;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class DeploymentConfig {
    private final @NotNull String localPath;

    private final @NotNull String deployPath;

    private final @NotNull String webPath;

    private final @NotNull String url;

    public DeploymentConfig(@NotNull String localPath, @NotNull String url) {
        this(localPath, "/var/www/html", "/", url);
    }

    public DeploymentConfig(@NotNull String localPath, @NotNull String deployPath, @NotNull String webPath, @NotNull String url) {
        this.localPath = localPath;
        this.deployPath = deployPath;
        this.webPath = webPath;
        this.url = url;
    }

    public @NotNull String getLocalPath() {
        return localPath;
    }

    public @NotNull String getDeployPath() {
        return deployPath;
    }

    public @NotNull String getWebPath() {
        return webPath;
    }

    public @NotNull String getUrl() {
        return url;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DeploymentConfig that = (DeploymentConfig) o;
        return getLocalPath().equals(that.getLocalPath()) && getDeployPath().equals(that.getDeployPath()) && getWebPath().equals(that.getWebPath()) && getUrl().equals(that.getUrl());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getLocalPath(), getDeployPath(), getWebPath(), getUrl());
    }

    @Override
    public String toString() {
        return "DeploymentConfig{" +
                "localPath='" + localPath + '\'' +
                ", deployPath='" + deployPath + '\'' +
                ", webPath='" + webPath + '\'' +
                ", url='" + url + '\'' +
                '}';
    }
}

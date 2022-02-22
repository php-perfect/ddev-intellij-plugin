package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Description {
    public enum Status {
        @SerializedName("running") RUNNING,
        @SerializedName("paused") PAUSED,
        @SerializedName("stopped") STOPPED,
        @SerializedName("starting") STARTING,
    }

    private final @Nullable String phpVersion;

    private final @Nullable Status status;

    @SerializedName("mailhog_https_url")
    private final @Nullable String mailHogHttpsUrl;

    @SerializedName("mailhog_url")
    private final @Nullable String mailHogHttpUrl;

    private final @Nullable Map<String, Service> services;

    public Description(@Nullable String phpVersion, @Nullable Status status, @Nullable String mailhogHttpsUrl, @Nullable String mailhogHttpUrl, @Nullable Map<String, Service> services) {
        this.phpVersion = phpVersion;
        this.status = status;
        this.mailHogHttpsUrl = mailhogHttpsUrl;
        this.mailHogHttpUrl = mailhogHttpUrl;
        this.services = services;
    }

    public @Nullable String getPhpVersion() {
        return this.phpVersion;
    }

    public @Nullable Status getStatus() {
        return this.status;
    }

    public @Nullable String getMailHogHttpsUrl() {
        return this.mailHogHttpsUrl;
    }

    public @Nullable String getMailHogHttpUrl() {
        return this.mailHogHttpUrl;
    }

    public @NotNull Map<String, Service> getServices() {
        if (this.services == null) {
            return new HashMap<>();
        }

        return this.services;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Description)) return false;
        Description that = (Description) o;
        return Objects.equals(getPhpVersion(), that.getPhpVersion()) && Objects.equals(getStatus(), that.getStatus()) && Objects.equals(getServices(), that.getServices());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getPhpVersion(), getStatus(), getServices());
    }

    @Override
    public String toString() {
        return "Description{" + "phpVersion='" + phpVersion + '\'' + ", status='" + status + '\'' + ", services=" + services + '}';
    }
}

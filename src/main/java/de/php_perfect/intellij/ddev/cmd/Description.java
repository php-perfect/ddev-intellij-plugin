package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

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

    private final @Nullable Map<String, Service> services;

    public Description(@Nullable String phpVersion, @Nullable Status status, @Nullable Map<String, Service> services) {
        this.phpVersion = phpVersion;
        this.status = status;
        this.services = services;
    }

    public @Nullable String getPhpVersion() {
        return phpVersion;
    }

    public @Nullable Status getStatus() {
        return status;
    }

    public @Nullable Map<String, Service> getServices() {
        return services;
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

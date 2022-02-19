package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Objects;

public class Description {
    public enum Status {
        @SerializedName("running")
        RUNNING,
        @SerializedName("paused")
        PAUSED,
        @SerializedName("stopped")
        STOPPED,
        @SerializedName("starting")
        STARTING,
        @SerializedName("undefined")
        UNDEFINED,
    }

    private @Nullable String phpVersion;

    private @Nullable String status;

    private Map<String, Service> services;

    public @Nullable String getPhpVersion() {
        return phpVersion;
    }

    public void setPhpVersion(@Nullable String phpVersion) {
        this.phpVersion = phpVersion;
    }

    public void setStatus(@Nullable String status) {
        this.status = status;
    }

    public @NotNull Status getStatus() {
        if (this.status == null) {
            return Status.UNDEFINED;
        }

        switch (this.status) {
            case "running":
                return Status.RUNNING;
            case "paused":
                return Status.PAUSED;
            case "stopped":
                return Status.STOPPED;
            case "starting":
                return Status.STARTING;
            default:
                return Status.UNDEFINED;
        }
    }

    public Map<String, Service> getServices() {
        return services;
    }

    public void setServices(Map<String, Service> services) {
        this.services = services;
    }

    @Override
    public int hashCode() {
        return Objects.hash(phpVersion, status);
    }
}

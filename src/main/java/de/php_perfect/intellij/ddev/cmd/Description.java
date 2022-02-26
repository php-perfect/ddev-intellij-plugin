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

    @SerializedName("dbinfo")
    private final @Nullable DatabaseInfo databaseInfo;

    public Description(@Nullable String phpVersion, @Nullable Status status, @Nullable String mailHogHttpsUrl, @Nullable String mailHogHttpUrl, @Nullable Map<String, Service> services, @Nullable DatabaseInfo databaseInfo) {
        this.phpVersion = phpVersion;
        this.status = status;
        this.mailHogHttpsUrl = mailHogHttpsUrl;
        this.mailHogHttpUrl = mailHogHttpUrl;
        this.services = services;
        this.databaseInfo = databaseInfo;
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

    public @Nullable DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Description that = (Description) o;
        return Objects.equals(phpVersion, that.phpVersion) && status == that.status && Objects.equals(mailHogHttpsUrl, that.mailHogHttpsUrl) && Objects.equals(mailHogHttpUrl, that.mailHogHttpUrl) && Objects.equals(services, that.services) && Objects.equals(databaseInfo, that.databaseInfo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phpVersion, status, mailHogHttpsUrl, mailHogHttpUrl, services, databaseInfo);
    }

    @Override
    public String toString() {
        return "Description{" +
                "phpVersion='" + phpVersion + '\'' +
                ", status=" + status +
                ", mailHogHttpsUrl='" + mailHogHttpsUrl + '\'' +
                ", mailHogHttpUrl='" + mailHogHttpUrl + '\'' +
                ", services=" + services +
                ", databaseInfo=" + databaseInfo +
                '}';
    }
}

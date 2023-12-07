package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public record DatabaseInfo(
        @SerializedName("database_type") de.php_perfect.intellij.ddev.cmd.DatabaseInfo.@Nullable Type type,
        @SerializedName("database_version") @Nullable String version, @SerializedName("dbPort") int port,
        @SerializedName("dbname") @Nullable String name, @SerializedName("host") @Nullable String host,
        @SerializedName("username") @Nullable String username, @SerializedName("password") @Nullable String password,
        @SerializedName("published_port") int publishedPort) {
    public enum Type {
        @SerializedName("mysql") MYSQL,
        @SerializedName("mariadb") MARIADB,
        @SerializedName("postgres") POSTGRESQL,
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseInfo that)) return false;
        return port == that.port && publishedPort == that.publishedPort && type == that.type && Objects.equals(version, that.version) && Objects.equals(name, that.name) && Objects.equals(host, that.host) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public String toString() {
        return "DatabaseInfo{" +
                "type=" + type +
                ", version='" + version + '\'' +
                ", port=" + port +
                ", name='" + name + '\'' +
                ", host='" + host + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", publishedPort=" + publishedPort +
                '}';
    }
}

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

    public DatabaseInfo(@Nullable Type type, @Nullable String version, int port, @Nullable String name, @Nullable String host, @Nullable String username, @Nullable String password, int publishedPort) {
        this.type = type;
        this.version = version;
        this.port = port;
        this.name = name;
        this.host = host;
        this.username = username;
        this.password = password;
        this.publishedPort = publishedPort;
    }

    @Override
    public @Nullable Type type() {
        return this.type;
    }

    @Override
    public @Nullable String version() {
        return this.version;
    }

    @Override
    public int port() {
        return this.port;
    }

    @Override
    public @Nullable String name() {
        return this.name;
    }

    @Override
    public @Nullable String host() {
        return this.host;
    }

    @Override
    public @Nullable String username() {
        return this.username;
    }

    @Override
    public @Nullable String password() {
        return this.password;
    }

    @Override
    public int publishedPort() {
        return this.publishedPort;
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

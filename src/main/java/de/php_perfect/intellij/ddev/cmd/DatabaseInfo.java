package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DatabaseInfo {
    public enum Type {
        @SerializedName("mysql") MYSQL,
        @SerializedName("mariadb") MARIADB,
        @SerializedName("postgres") POSTGRESQL,
    }

    @SerializedName("database_type")
    private final @Nullable Type type;

    @SerializedName("database_version")
    private final @Nullable String version;

    @SerializedName("dbPort")
    private final int port;

    @SerializedName("dbname")
    private final @Nullable String name;

    @SerializedName("host")
    private final @Nullable String host;

    @SerializedName("username")
    private final @Nullable String username;

    @SerializedName("password")
    private final @Nullable String password;

    @SerializedName("published_port")
    private final int publishedPort;

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

    public @Nullable Type getType() {
        return this.type;
    }

    public @Nullable String getVersion() {
        return this.version;
    }

    public int getPort() {
        return this.port;
    }

    public @Nullable String getName() {
        return this.name;
    }

    public @Nullable String getHost() {
        return this.host;
    }

    public @Nullable String getUsername() {
        return this.username;
    }

    public @Nullable String getPassword() {
        return this.password;
    }

    public int getPublishedPort() {
        return this.publishedPort;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DatabaseInfo)) return false;
        DatabaseInfo that = (DatabaseInfo) o;
        return port == that.port && publishedPort == that.publishedPort && type == that.type && Objects.equals(version, that.version) && Objects.equals(name, that.name) && Objects.equals(host, that.host) && Objects.equals(username, that.username) && Objects.equals(password, that.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, version, port, name, host, username, password, publishedPort);
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

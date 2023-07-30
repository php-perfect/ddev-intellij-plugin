package de.php_perfect.intellij.ddev.state;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.version.Version;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class StateImpl implements State {
    private @Nullable Version version = null;

    private @Nullable Description description = null;

    private @Nullable String ddevBinary = null;

    private boolean configured = false;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean isBinaryConfigured() {
        return this.ddevBinary != null && !this.ddevBinary.isEmpty();
    }

    @Override
    public boolean isAvailable() {
        return this.version != null;
    }

    @Override
    public @Nullable String getDdevBinary() {
        return ddevBinary;
    }

    public void setDdevBinary(@Nullable String ddevBinary) {
        this.ddevBinary = ddevBinary;
    }

    @Override
    public boolean isConfigured() {
        return this.configured;
    }

    public void setConfigured(boolean configured) {
        this.configured = configured;
    }

    public void setDdevVersion(@Nullable Version ddevVersion) {
        this.readWriteLock.writeLock().lock();
        try {
            this.version = ddevVersion;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public @Nullable Version getDdevVersion() {
        this.readWriteLock.readLock().lock();
        try {
            return this.version;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    @Override
    public @Nullable Description getDescription() {
        this.readWriteLock.readLock().lock();
        try {
            return this.description;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    public void setDescription(@Nullable Description description) {
        this.readWriteLock.writeLock().lock();
        try {
            this.description = description;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    public void reset() {
        this.readWriteLock.writeLock().lock();
        try {
            this.version = null;
            this.description = null;
            this.ddevBinary = null;
            this.configured = false;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StateImpl state = (StateImpl) o;
        return isConfigured() == state.isConfigured() && Objects.equals(version, state.version) && Objects.equals(getDescription(), state.getDescription()) && Objects.equals(getDdevBinary(), state.getDdevBinary());
    }

    @Override
    public int hashCode() {
        return Objects.hash(version, getDescription(), getDdevBinary(), isConfigured());
    }

    @Override
    public String toString() {
        return "StateImpl{" +
                "version=" + version +
                ", description=" + description +
                ", ddevBinary='" + ddevBinary + "'" +
                ", configured=" + configured +
                '}';
    }
}

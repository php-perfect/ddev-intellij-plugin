package de.php_perfect.intellij.ddev.state;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.TestOnly;

import java.util.Objects;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class StateImpl implements State {
    private @Nullable Versions versions = null;

    private @Nullable Description description = null;

    private boolean installed = false;

    private @Nullable String ddevBinary = null;

    private boolean configured = false;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean isInstalled() {
        return this.installed;
    }

    public void setInstalled(boolean installed) {
        this.installed = installed;
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

    @Override
    public @Nullable Versions getVersions() {
        this.readWriteLock.readLock().lock();
        try {
            return this.versions;
        } finally {
            this.readWriteLock.readLock().unlock();
        }
    }

    public void setVersions(@Nullable Versions versions) {
        this.readWriteLock.writeLock().lock();
        try {
            this.versions = versions;
        } finally {
            this.readWriteLock.writeLock().unlock();
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof StateImpl)) return false;
        StateImpl state = (StateImpl) o;
        return installed == state.installed && configured == state.configured && Objects.equals(versions, state.versions) && Objects.equals(description, state.description) && Objects.equals(ddevBinary, state.ddevBinary);
    }

    @Override
    public int hashCode() {
        return Objects.hash(versions, description, installed, ddevBinary, configured);
    }

    @Override
    public String toString() {
        return "StateImpl{" +
                "versions=" + versions +
                ", description=" + description +
                ", installed=" + installed +
                ", ddevBinary='" + ddevBinary + '\'' +
                ", configured=" + configured +
                ", readWriteLock=" + readWriteLock +
                '}';
    }

    @TestOnly
    public void reset() {
        this.readWriteLock.writeLock().lock();
        try {
            this.versions = null;
            this.description = null;
            this.installed = false;
            this.ddevBinary = null;
            this.configured = false;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }
}

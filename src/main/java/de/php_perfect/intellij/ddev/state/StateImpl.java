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
        if (o == null || getClass() != o.getClass()) return false;
        StateImpl state = (StateImpl) o;
        return isInstalled() == state.isInstalled() && isConfigured() == state.isConfigured() && Objects.equals(getVersions(), state.getVersions()) && Objects.equals(getDescription(), state.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getVersions(), getDescription(), isInstalled(), isConfigured());
    }

    @Override
    public String toString() {
        return "StateImpl{" +
                "versions=" + versions +
                ", description=" + description +
                ", installed=" + installed +
                ", configured=" + configured +
                '}';
    }

    @TestOnly
    public void reset() {
        this.readWriteLock.writeLock().lock();
        try {
            this.versions = null;
            this.description = null;
            this.installed = false;
            this.configured = false;
        } finally {
            this.readWriteLock.writeLock().unlock();
        }
    }
}

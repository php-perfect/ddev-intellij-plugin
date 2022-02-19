package de.php_perfect.intellij.ddev.state;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

final class StateImpl implements State {
    private @Nullable Versions versions;

    private @Nullable Description description;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    @Override
    public boolean isInstalled() {
        return this.getVersions() != null;
    }

    @Override
    public boolean isConfigured() {
        return this.getDescription() != null;
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
}

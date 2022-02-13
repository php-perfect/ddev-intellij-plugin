package de.php_perfect.intellij.ddev.config;

import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public final class DdevState {
    private @Nullable Versions versions;

    private @Nullable Description description;

    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

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

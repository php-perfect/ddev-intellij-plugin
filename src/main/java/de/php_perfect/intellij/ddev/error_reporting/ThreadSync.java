package de.php_perfect.intellij.ddev.error_reporting;

import java.util.concurrent.atomic.AtomicReference;

public class ThreadSync {
    private final Object syncObj = new Object();
    private final AtomicReference<Boolean> waiting = new AtomicReference<>(true);

    public void waitForRelease() {
        synchronized (this.syncObj) {
            while (this.waiting.get()) {
                try {
                    this.syncObj.wait();
                } catch (Exception ignore) {
                }
            }
        }
    }

    public void release() {
        synchronized (this.syncObj) {
            this.waiting.set(false);
            this.syncObj.notifyAll();
        }
    }

    public void reset() {
        this.waiting.set(true);
    }
}

package de.php_perfect.intellij.ddev.state;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class StateWatcherTest extends BasePlatformTestCase {

    private StateWatcherImpl stateWatcher;

    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
        this.stateWatcher = new StateWatcherImpl(this.getProject());
    }

    @Test
    void startWatching() {
        stateWatcher.startWatching();
    }

    @Test
    void startWatchingTwice() {
        stateWatcher.startWatching();
        stateWatcher.startWatching();
    }

    @Test
    void startStopWatching() {
        stateWatcher.startWatching();
        stateWatcher.stopWatching();
    }

    @Test
    void startStopWatchingWithoutStart() {
        stateWatcher.stopWatching();
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        this.stateWatcher.dispose();
        super.tearDown();
    }
}
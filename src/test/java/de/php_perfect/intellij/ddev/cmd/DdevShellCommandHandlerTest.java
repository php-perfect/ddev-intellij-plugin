package de.php_perfect.intellij.ddev.cmd;

import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public final class DdevShellCommandHandlerTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    void nonDdevCommand() {
        final DdevShellCommandHandlerImpl ddevShellCommandHandlerImpl = new DdevShellCommandHandlerImpl();

        Assertions.assertFalse(ddevShellCommandHandlerImpl.matches(this.getProject(), null, true, "cat abc"));
    }

    @Test
    void incompleteDdevCommand() {
        final DdevShellCommandHandlerImpl ddevShellCommandHandlerImpl = new DdevShellCommandHandlerImpl();

        Assertions.assertFalse(ddevShellCommandHandlerImpl.matches(this.getProject(), null, true, "ddev "));
    }

    @Test
    void unkownDdevCommand() {
        final DdevShellCommandHandlerImpl ddevShellCommandHandlerImpl = new DdevShellCommandHandlerImpl();

        Assertions.assertFalse(ddevShellCommandHandlerImpl.matches(this.getProject(), null, true, "ddev foo"));
    }

    @Test
    void ddevCommand() {
        final DdevShellCommandHandlerImpl ddevShellCommandHandlerImpl = new DdevShellCommandHandlerImpl();

        Assertions.assertTrue(ddevShellCommandHandlerImpl.matches(this.getProject(), null, true, "ddev start"));
    }
}

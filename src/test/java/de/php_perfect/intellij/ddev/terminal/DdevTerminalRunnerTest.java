package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.project.Project;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.pty4j.PtyProcess;
import org.jetbrains.plugins.terminal.TerminalProcessOptions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

final class DdevTerminalRunnerTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void createProcess() throws ExecutionException {
        Project project = getProject();
        DdevTerminalRunner ddevTerminalRunner = new DdevTerminalRunner(project);

        Assertions.assertInstanceOf(PtyProcess.class, ddevTerminalRunner.createProcess(new TerminalProcessOptions(project.getBasePath(), null, null), null));
    }

    @Test
    public void terminalIsNotPersistent() {
        Project project = getProject();
        DdevTerminalRunner ddevTerminalRunner = new DdevTerminalRunner(project);

        Assertions.assertFalse(ddevTerminalRunner.isTerminalSessionPersistent());
    }
}

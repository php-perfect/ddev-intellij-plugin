package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import com.pty4j.PtyProcess;
import de.php_perfect.intellij.ddev.state.DdevConfigLoader;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.MockDdevConfigLoader;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.plugins.terminal.TerminalProcessOptions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.concurrent.ExecutionException;

final class DdevTerminalRunnerTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void createProcess() throws ExecutionException, NoSuchFieldException, IllegalAccessException {
        Project project = getProject();
        DdevTerminalRunner ddevTerminalRunner = new DdevTerminalRunner(project);

        State state = DdevStateManager.getInstance(project).getState();

        Field field = state.getClass().getDeclaredField("ddevBinary");
        field.setAccessible(true);

        if (SystemInfo.isWindows) {
            field.set(state, "where");
        } else {
            field.set(state, "which");
        }

        Assertions.assertInstanceOf(PtyProcess.class, ddevTerminalRunner.createProcess(new TerminalProcessOptions(project.getBasePath(), null, null), null));
    }

    @Test
    public void createProcessNotExistentDdev() throws ExecutionException, NoSuchFieldException, IllegalAccessException {
        Project project = getProject();
        DdevTerminalRunner ddevTerminalRunner = new DdevTerminalRunner(project);

        State state = DdevStateManager.getInstance(project).getState();

        Field field = state.getClass().getDeclaredField("ddevBinary");
        field.setAccessible(true);
        field.set(state, null);

        Assertions.assertThrowsExactly(ExecutionException.class, () -> ddevTerminalRunner.createProcess(new TerminalProcessOptions(project.getBasePath(), null, null), null));
    }

    @Test
    public void terminalIsNotPersistent() {
        Project project = getProject();
        DdevTerminalRunner ddevTerminalRunner = new DdevTerminalRunner(project);

        Assertions.assertFalse(ddevTerminalRunner.isTerminalSessionPersistent());
    }

    @Override
    @AfterEach
    protected void tearDown() throws Exception {
        final MockDdevConfigLoader ddevConfigLoader = (MockDdevConfigLoader) DdevConfigLoader.getInstance(this.getProject());
        ddevConfigLoader.setExists(false);

        DdevStateManager.getInstance(this.getProject()).resetState();

        super.tearDown();
    }
}

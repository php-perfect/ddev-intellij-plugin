package de.php_perfect.intellij.ddev.state;

import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.cmd.MockProcessExecutor;
import de.php_perfect.intellij.ddev.cmd.ProcessExecutor;
import de.php_perfect.intellij.ddev.cmd.Versions;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicBoolean;

final class DdevStateManagerImplTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    void initialize() {
        AtomicBoolean runnableExecuted = new AtomicBoolean(false);
        DdevStateManager ddevStateManager = DdevStateManager.getInstance(this.getProject());

        this.prepareCommand("ddev version --json-output", "src/test/resources/ddev_version.json");
        this.prepareCommand("ddev describe --json-output", "src/test/resources/ddev_describe.json");

        ddevStateManager.initialize(() -> runnableExecuted.set(true));

        StateImpl expectedState = new StateImpl();
        expectedState.setVersions(new Versions("v1.19.0"));
        expectedState.setDescription(new Description("8.1", Description.Status.STOPPED, null, null, new HashMap<>(), null));

        Assertions.assertEquals(expectedState, ddevStateManager.getState());
        Assertions.assertTrue(runnableExecuted.get());
    }

    @Test
    void updateDescription() {
        DdevStateManager ddevStateManager = DdevStateManager.getInstance(this.getProject());

        this.prepareCommand("ddev version --json-output", "src/test/resources/ddev_version.json");
        this.prepareCommand("ddev describe --json-output", "src/test/resources/ddev_describe.json");

        ddevStateManager.initialize(null);

        StateImpl expectedState = new StateImpl();
        expectedState.setVersions(new Versions("v1.19.0"));
        expectedState.setDescription(new Description("8.1", Description.Status.STOPPED, null, null, new HashMap<>(), null));

        Assertions.assertEquals(expectedState, ddevStateManager.getState());

        this.prepareCommand("ddev describe --json-output", "src/test/resources/ddev_describe2.json");

        ddevStateManager.updateDescription();

        expectedState.setDescription(new Description("7.4", Description.Status.STOPPED, null, null, new HashMap<>(), null));

        Assertions.assertEquals(expectedState, ddevStateManager.getState());
    }

    private void prepareCommand(String command, String file) {
        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);

        ProcessOutput processOutput = null;
        try {
            processOutput = new ProcessOutput(Files.readString(Path.of(file)), "", 0, false, false);
        } catch (IOException e) {
            Assertions.fail(e);
        }

        mockProcessExecutor.addProcessOutput(command, processOutput);
    }
}
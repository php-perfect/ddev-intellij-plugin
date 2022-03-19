package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;

final class DdevImplTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findBinary() throws CommandFailedException {
        String expectedWhich = "which";
        if (SystemInfo.isWindows) {
            expectedWhich = "where";
        }

        ProcessOutput processOutput = new ProcessOutput("/foo/bar/bin/ddev", "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput(expectedWhich + " ddev", processOutput);

        Assertions.assertEquals("/foo/bar/bin/ddev", new DdevImpl().findBinary(getProject()));
    }

    @Test
    public void isNotInstalled() throws CommandFailedException {
        String expectedWhich = "which";
        if (SystemInfo.isWindows) {
            expectedWhich = "where";
        }

        ProcessOutput processOutput = new ProcessOutput("", "", 1, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput(expectedWhich + " ddev", processOutput);

        Assertions.assertNull(new DdevImpl().findBinary(getProject()));
    }

    @Test
    public void version() throws CommandFailedException, IOException {
        Versions expected = new Versions("v1.19.0");

        ProcessOutput processOutput = new ProcessOutput(Files.readString(Path.of("src/test/resources/ddev_version.json")), "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("ddev version --json-output", processOutput);

        Assertions.assertEquals(expected, new DdevImpl().version(getProject()));
    }

    @Test
    public void describe() throws CommandFailedException, IOException {
        Description expected = new Description("acol", "8.1", Description.Status.STOPPED, null, null, new HashMap<>(), null);

        ProcessOutput processOutput = new ProcessOutput(Files.readString(Path.of("src/test/resources/ddev_describe.json")), "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("ddev describe --json-output", processOutput);

        Assertions.assertEquals(expected, new DdevImpl().describe(getProject()));
    }
}

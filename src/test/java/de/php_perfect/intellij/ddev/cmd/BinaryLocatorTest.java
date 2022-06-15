package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.util.SystemInfo;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class BinaryLocatorTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void findBinary() {
        String expectedWhich = "which";
        if (SystemInfo.isWindows) {
            expectedWhich = "where";
        }

        ProcessOutput processOutput = new ProcessOutput("/foo/bar/bin/ddev", "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput(expectedWhich + " ddev", processOutput);

        Assertions.assertEquals("/foo/bar/bin/ddev", new BinaryLocatorImpl().findInPath(getProject()));
    }

    @Test
    public void isNotInstalled() {
        String expectedWhich = "which";
        if (SystemInfo.isWindows) {
            expectedWhich = "where";
        }

        ProcessOutput processOutput = new ProcessOutput("", "", 1, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput(expectedWhich + " ddev", processOutput);

        Assertions.assertNull(new BinaryLocatorImpl().findInPath(getProject()));
    }
}

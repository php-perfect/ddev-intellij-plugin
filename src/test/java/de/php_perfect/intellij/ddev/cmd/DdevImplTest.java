package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.apache.velocity.util.StringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

public class DdevImplTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void version() throws CommandFailedException {
        Versions expected = new Versions("v1.19.0");

        ProcessOutput processOutput = new ProcessOutput(StringUtils.fileContentsToString("src/test/resources/ddev_version.json"), "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("ddev version --json-output", processOutput);

        Assertions.assertEquals(expected, new DdevImpl().version(getProject()));
    }

    @Test
    public void describe() throws CommandFailedException {
        Description expected = new Description("8.1", Description.Status.STOPPED, new HashMap<>());

        ProcessOutput processOutput = new ProcessOutput(StringUtils.fileContentsToString("src/test/resources/ddev_describe.json"), "", 0, false, false);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("ddev describe --json-output", processOutput);

        Assertions.assertEquals(expected, new DdevImpl().describe(getProject()));
    }
}
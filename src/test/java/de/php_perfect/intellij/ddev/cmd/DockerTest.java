package de.php_perfect.intellij.ddev.cmd;

import com.intellij.execution.process.ProcessOutput;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.testFramework.fixtures.BasePlatformTestCase;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

final class DockerTest extends BasePlatformTestCase {
    @Override
    @BeforeEach
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Test
    void dockerIsRunning() {
        ProcessOutput processOutput = new ProcessOutput(0);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("docker info", processOutput);

        Assertions.assertTrue(new DockerImpl().isRunning(this.getProject().getBasePath()));
    }

    @Test
    void dockerIsNotRunning() {
        ProcessOutput processOutput = new ProcessOutput(1);

        MockProcessExecutor mockProcessExecutor = (MockProcessExecutor) ApplicationManager.getApplication().getService(ProcessExecutor.class);
        mockProcessExecutor.addProcessOutput("docker info", processOutput);

        Assertions.assertFalse(new DockerImpl().isRunning(this.getProject().getBasePath()));
    }
}

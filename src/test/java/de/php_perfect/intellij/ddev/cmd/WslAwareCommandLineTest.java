package de.php_perfect.intellij.ddev.cmd;

import com.intellij.openapi.util.SystemInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Test;

public class WslAwareCommandLineTest {
    @Test
    public void testNonWslPathIsOnWindows() {
        Assumptions.assumeTrue(SystemInfo.isWindows);

        String expected = "echo test";

        String path = "C:\\Users\\nl\\IdeaProjects\\ddev-intellij-plugin";
        WslAwareCommandLine commandLine = new WslAwareCommandLine(path, "echo", "test");

        Assertions.assertEquals(expected, commandLine.getCommandLineString());
    }

    @Test
    public void testWslPathIsDetectedOnWindows() {
        Assumptions.assumeTrue(SystemInfo.isWindows);

        String expected = "wsl -d Ubuntu bash -l -c \"echo test\"";

        String path = "//wsl$/Ubuntu/home/nl/projects/acol";
        WslAwareCommandLine commandLine = new WslAwareCommandLine(path, "echo", "test");

        Assertions.assertEquals(expected, commandLine.getCommandLineString());
    }
}

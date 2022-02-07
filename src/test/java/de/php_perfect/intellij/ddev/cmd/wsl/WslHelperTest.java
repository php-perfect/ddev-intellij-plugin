package de.php_perfect.intellij.ddev.cmd.wsl;

import de.php_perfect.intellij.ddev.cmd.wsl.WslHelper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class WslHelperTest {
    @Test
    public void testDetectWslPath() {
        String workspace = "//wsl$/Ubuntu/home/nl/projects/acol";

        Assertions.assertTrue(WslHelper.isWslPath(workspace));
    }

    @Test
    public void testDetectNonWslPath() {
        String workspace = "C:\\Users\\nl\\IdeaProjects\\ddev-intellij-plugin";

        Assertions.assertFalse(WslHelper.isWslPath(workspace));
    }

    @Test
    public void testParseDistroForNonWslPath() {
        String workspace = "C:\\Users\\nl\\IdeaProjects\\ddev-intellij-plugin";

        Assertions.assertNull(WslHelper.parseWslDistro(workspace));
    }

    @Test
    public void testParseDistro() {
        String workspace = "//wsl$/Ubuntu/home/nl/projects/acol";

        Assertions.assertEquals("Ubuntu", WslHelper.parseWslDistro(workspace));
    }

    @Test
    public void testParseOtherDistro() {
        String workspace = "//wsl$/docker-desktop-data/home/nl/projects/acol";

        Assertions.assertEquals("docker-desktop-data", WslHelper.parseWslDistro(workspace));
    }
}

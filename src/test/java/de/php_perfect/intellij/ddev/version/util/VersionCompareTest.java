package de.php_perfect.intellij.ddev.version.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class VersionCompareTest {
    @Test
    public void needsUpdateMajor() {
        Assertions.assertTrue(VersionCompare.needsUpdate("1.0.0", "2.0.0"));
    }

    @Test
    public void needsUpdateMinor() {
        Assertions.assertTrue(VersionCompare.needsUpdate("1.0.0", "1.1.0"));
    }

    @Test
    public void needsUpdatePatch() {
        Assertions.assertTrue(VersionCompare.needsUpdate("1.0.0", "1.0.1"));
    }

    @Test
    public void needsNoUpdateSame() {
        Assertions.assertFalse(VersionCompare.needsUpdate("1.0.0", "1.0.0"));
    }

    @Test
    public void needsNoUpdateOnRc() {
        Assertions.assertFalse(VersionCompare.needsUpdate("v1.19.0-rc1", "1.18.9"));
    }

    @Test
    public void needsNoUpdateLowerMinor() {
        Assertions.assertFalse(VersionCompare.needsUpdate("1.0.0", "0.9.0"));
    }
}

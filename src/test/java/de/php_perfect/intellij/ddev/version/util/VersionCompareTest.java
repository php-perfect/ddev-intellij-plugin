package de.php_perfect.intellij.ddev.version.util;

import de.php_perfect.intellij.ddev.version.Version;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class VersionCompareTest {
    @Test
    void needsUpdateMajor() {
        Assertions.assertTrue(VersionCompare.needsUpdate(new Version("1.0.0"), new Version("2.0.0")));
    }

    @Test
    void needsUpdateMinor() {
        Assertions.assertTrue(VersionCompare.needsUpdate(new Version("1.0.0"), new Version("1.1.0")));
    }

    @Test
    void needsUpdatePatch() {
        Assertions.assertTrue(VersionCompare.needsUpdate(new Version("1.0.0"), new Version("1.0.1")));
    }

    @Test
    void needsNoUpdateSame() {
        Assertions.assertFalse(VersionCompare.needsUpdate(new Version("1.0.0"), new Version("1.0.0")));
    }

    @Test
    void needsNoUpdateOnRc() {
        Assertions.assertFalse(VersionCompare.needsUpdate(new Version("v1.19.0-rc1"), new Version("1.18.9")));
    }

    @Test
    void needsNoUpdateLowerMinor() {
        Assertions.assertFalse(VersionCompare.needsUpdate(new Version("1.0.0"), new Version("0.9.0")));
    }
}

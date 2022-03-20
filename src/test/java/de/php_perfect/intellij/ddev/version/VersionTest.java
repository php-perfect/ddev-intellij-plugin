package de.php_perfect.intellij.ddev.version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

final class VersionTest {
    @Test
    public void newInstance_withTwoDotRelease_isParsedCorrectly() {
        final Version version = new Version("1.26.6");

        Assertions.assertArrayEquals(new int[]{1, 26, 6}, version.numbers);
    }

    @Test
    public void newInstance_withTwoDotRelease_andVersionPrefix_isParsedCorrectly() {
        final Version version = new Version("v1.26.6");

        Assertions.assertArrayEquals(new int[]{1, 26, 6}, version.numbers);
    }

    @Test
    public void newInstance_withTwoDotReleaseAndPreReleaseName_isParsedCorrectly() {
        final Version version = new Version("1.26.6-DEBUG");

        Assertions.assertArrayEquals(new int[]{1, 26, 6}, version.numbers);
    }

    @Test
    public void compareTo_withEarlierVersion_isGreaterThan() {
        Assertions.assertEquals(1, new Version("2.0.0").compareTo(new Version("1.0.0")));
    }

    @Test
    public void compareTo_withSameVersion_isEqual() {
        Assertions.assertEquals(0, new Version("2.0.0").compareTo(new Version("2.0.0")));
    }

    @Test
    public void compareTo_withLaterVersion_isLessThan() {
        Assertions.assertEquals(-1, new Version("1.0.0").compareTo(new Version("2.0.0")));
    }

    @Test
    public void compareTo_withMorePreciseSameVersion_isFalse() {
        Assertions.assertEquals(0, new Version("1").compareTo(new Version("1.0.0")));
    }

    @Test
    public void compareTo_withMorePreciseEarlierVersion_isFalse() {
        Assertions.assertEquals(1, new Version("2").compareTo(new Version("1.0.0")));
    }

    @Test
    public void compareTo_withMorePreciseLaterVersion_isLessThan() {
        Assertions.assertEquals(-1, new Version("1").compareTo(new Version("1.0.1")));
    }
}

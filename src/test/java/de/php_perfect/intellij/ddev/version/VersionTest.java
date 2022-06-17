package de.php_perfect.intellij.ddev.version;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

final class VersionTest {
    @ParameterizedTest
    @ValueSource(strings = {"1.26.6", "v1.26.6", "1.26.6-DEBUG"})
    void versionsWithPrefixAndSuffixAreParsedCorrectly(String versionString) {
        final Version version = new Version(versionString);

        Assertions.assertArrayEquals(new int[]{1, 26, 6}, version.numbers);
    }

    @Test
    void compareTo_withEarlierVersion_isGreaterThan() {
        Assertions.assertEquals(1, new Version("2.0.0").compareTo(new Version("1.0.0")));
    }

    @Test
    void compareTo_withSameVersion_isEqual() {
        Assertions.assertEquals(0, new Version("2.0.0").compareTo(new Version("2.0.0")));
    }

    @Test
    void compareTo_withLaterVersion_isLessThan() {
        Assertions.assertEquals(-1, new Version("1.0.0").compareTo(new Version("2.0.0")));
    }

    @Test
    void compareTo_withMorePreciseSameVersion_isFalse() {
        Assertions.assertEquals(0, new Version("1").compareTo(new Version("1.0.0")));
    }

    @Test
    void compareTo_withMorePreciseEarlierVersion_isFalse() {
        Assertions.assertEquals(1, new Version("2").compareTo(new Version("1.0.0")));
    }

    @Test
    void compareTo_withMorePreciseLaterVersion_isLessThan() {
        Assertions.assertEquals(-1, new Version("1").compareTo(new Version("1.0.1")));
    }
}

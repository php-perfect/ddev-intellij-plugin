package de.php_perfect.intellij.ddev.version.util;

import de.php_perfect.intellij.ddev.version.Version;

public final class VersionCompare {
    public static boolean needsUpdate(String currentVersion, String latestVersion) {
        final Version current = new Version(currentVersion);
        final Version latest = new Version(latestVersion);

        return current.compareTo(latest) < 0;
    }

    private VersionCompare() {
    }
}

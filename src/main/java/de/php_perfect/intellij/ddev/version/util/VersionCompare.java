package de.php_perfect.intellij.ddev.version.util;

import de.php_perfect.intellij.ddev.version.Version;

public final class VersionCompare {
    public static boolean needsUpdate(Version currentVersion, Version latestVersion) {
        return currentVersion.compareTo(latestVersion) < 0;
    }

    private VersionCompare() {
    }
}

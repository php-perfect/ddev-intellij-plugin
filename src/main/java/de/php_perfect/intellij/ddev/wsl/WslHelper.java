package de.php_perfect.intellij.ddev.wsl;

import org.jetbrains.annotations.Nullable;

public class WslHelper {
    private static final String WSL_ROOT = "//wsl$/";

    public static boolean isWslPath(@Nullable String path) {
        if (path == null) {
            return false;
        }

        return path.startsWith(WSL_ROOT);
    }

    @Nullable
    public static String parseWslDistro(@Nullable String path) {
        if (!isWslPath(path)) {
            return null;
        }

        String replacedString = path.replace(WSL_ROOT, "");

        return replacedString.substring(0, replacedString.indexOf('/'));
    }
}

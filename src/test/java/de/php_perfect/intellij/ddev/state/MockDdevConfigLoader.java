package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.Nullable;

public final class MockDdevConfigLoader implements DdevConfigLoader {
    private boolean exists = false;

    public void setExists(boolean exists) {
        this.exists = exists;
    }

    @Override
    public boolean exists() {
        return this.exists;
    }

    @Override
    public @Nullable VirtualFile load() {
        return null;
    }
}

package de.php_perfect.intellij.ddev.config;

import de.php_perfect.intellij.ddev.StateInitializedListener;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public final class LoadSchemaListener implements StateInitializedListener {
    @Override
    public void onStateInitialized(@NotNull State state) {
        if (state.getVersions() != null && state.getVersions().getDdevVersion() != null) {
            SchemaProvider.getInstance().loadSchema(state.getVersions().getDdevVersion());
        }
    }
}

package de.php_perfect.intellij.ddev.settings;

import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.project.Project;
import com.intellij.util.xmlb.XmlSerializerUtil;
import org.jetbrains.annotations.NotNull;

@State(name = "de.php_perfect.intellij.ddev.settings.DdevSettingsState", storages = @Storage("DdevIntegration.xml"))
public final class DdevSettingsState implements PersistentStateComponent<DdevSettingsState> {
    public @NotNull String ddevBinary = "";
    public boolean checkForUpdates = true;
    public boolean watchDdev = true;
    public boolean autoConfigureDataSource = true;
    public boolean autoConfigurePhpInterpreter = true;

    public static @NotNull DdevSettingsState getInstance(Project project) {
        return project.getService(DdevSettingsState.class);
    }

    @Override
    public @NotNull DdevSettingsState getState() {
        return this;
    }

    @Override
    public void loadState(@NotNull DdevSettingsState state) {
        XmlSerializerUtil.copyBean(state, this);
    }
}

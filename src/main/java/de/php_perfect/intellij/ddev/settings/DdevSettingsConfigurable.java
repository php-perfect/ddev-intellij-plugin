package de.php_perfect.intellij.ddev.settings;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class DdevSettingsConfigurable implements Configurable {
    private DdevSettingsComponent ddevSettingsComponent;

    private final @NotNull Project project;

    public DdevSettingsConfigurable(@NotNull Project project) {
        this.project = project;
    }

    @Nls(capitalization = Nls.Capitalization.Title)
    @Override
    public String getDisplayName() {
        return DdevIntegrationBundle.message("settings.headline");
    }

    @Override
    public JComponent getPreferredFocusedComponent() {
        return this.ddevSettingsComponent.getPreferredFocusedComponent();
    }

    @Override
    public JComponent createComponent() {
        this.ddevSettingsComponent = new DdevSettingsComponent();

        return this.ddevSettingsComponent.getPanel();
    }

    @Override
    public boolean isModified() {
        DdevSettingsState settings = DdevSettingsState.getInstance(this.project);
        boolean modified = this.ddevSettingsComponent.getCheckForUpdatedStatus() != settings.checkForUpdates;
        modified |= this.ddevSettingsComponent.getWatchDdevCheckboxStatus() != settings.watchDdev;

        return modified;
    }

    @Override
    public void apply() {
        DdevSettingsState settings = DdevSettingsState.getInstance(this.project);
        settings.checkForUpdates = this.ddevSettingsComponent.getCheckForUpdatedStatus();
        settings.watchDdev = this.ddevSettingsComponent.getWatchDdevCheckboxStatus();
        DdevNotifier.getInstance(this.project).asyncNotifyRestartAfterSettingsChange();
    }

    @Override
    public void reset() {
        DdevSettingsState settings = DdevSettingsState.getInstance(this.project);
        this.ddevSettingsComponent.setCheckForUpdatesStatus(settings.checkForUpdates);
        this.ddevSettingsComponent.setWatchDdevCheckboxStatus(settings.watchDdev);
    }

    @Override
    public void disposeUIResources() {
        this.ddevSettingsComponent = null;
    }
}

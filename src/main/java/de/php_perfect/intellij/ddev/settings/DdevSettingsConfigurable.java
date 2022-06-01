package de.php_perfect.intellij.ddev.settings;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.progress.ProgressManager;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.cmd.CommandFailedException;
import de.php_perfect.intellij.ddev.cmd.Ddev;
import de.php_perfect.intellij.ddev.state.DdevStateManager;
import de.php_perfect.intellij.ddev.state.StateWatcher;
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
        return DdevIntegrationBundle.message("settings.title");
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
        boolean modified = !this.ddevSettingsComponent.getDdevBinary().equals(settings.ddevBinary);
        modified |= this.ddevSettingsComponent.getCheckForUpdatedStatus() != settings.checkForUpdates;
        modified |= this.ddevSettingsComponent.getWatchDdevCheckboxStatus() != settings.watchDdev;
        modified |= this.ddevSettingsComponent.getAutoConfigureDataSource() != settings.autoConfigureDataSource;
        modified |= this.ddevSettingsComponent.getAutoConfigurePhpInterpreter() != settings.autoConfigurePhpInterpreter;

        return modified;
    }

    @Override
    public void apply() throws ConfigurationException {
        String newBinary = this.ddevSettingsComponent.getDdevBinary();

        if (!newBinary.equals("")) {
            verifyBinary(newBinary);
        }

        DdevSettingsState settings = DdevSettingsState.getInstance(this.project);
        settings.ddevBinary = newBinary;
        settings.checkForUpdates = this.ddevSettingsComponent.getCheckForUpdatedStatus();
        settings.watchDdev = this.ddevSettingsComponent.getWatchDdevCheckboxStatus();
        settings.autoConfigureDataSource = this.ddevSettingsComponent.getAutoConfigureDataSource();
        settings.autoConfigurePhpInterpreter = this.ddevSettingsComponent.getAutoConfigurePhpInterpreter();

        StateWatcher.getInstance(this.project).stopWatching();
        ApplicationManager.getApplication().executeOnPooledThread(() -> DdevStateManager.getInstance(this.project).initialize());
    }

    private void verifyBinary(String newBinary) throws ConfigurationException {
        try {
            ProgressManager.getInstance().runProcessWithProgressSynchronously(
                    () -> Ddev.getInstance().version(newBinary, project),
                    DdevIntegrationBundle.message("settings.process.verifyBinary.title"),
                    false,
                    this.project
            );
        } catch (CommandFailedException exception) {
            throw new ConfigurationException(
                    DdevIntegrationBundle.message("settings.message.invalidBinary.message", newBinary),
                    exception,
                    DdevIntegrationBundle.message("settings.message.invalidBinary.title")
            );
        }
    }

    @Override
    public void reset() {
        DdevSettingsState settings = DdevSettingsState.getInstance(this.project);
        this.ddevSettingsComponent.setDdevBinary(settings.ddevBinary);
        this.ddevSettingsComponent.setCheckForUpdatesStatus(settings.checkForUpdates);
        this.ddevSettingsComponent.setWatchDdevCheckboxStatus(settings.watchDdev);
        this.ddevSettingsComponent.setAutoConfigureDataSource(settings.autoConfigureDataSource);
        this.ddevSettingsComponent.setAutoConfigurePhpInterpreter(settings.autoConfigurePhpInterpreter);
    }

    @Override
    public void disposeUIResources() {
        this.ddevSettingsComponent = null;
    }
}

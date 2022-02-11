package de.php_perfect.intellij.ddev.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UI;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public final class DdevSettingsComponent {
    private final @NotNull JPanel jPanel;
    private final @NotNull JBCheckBox checkForUpdatesCheckbox = new JBCheckBox(DdevIntegrationBundle.message("settings.check_for_updates"));
    private final @NotNull JBCheckBox watchDdevCheckbox = new JBCheckBox(DdevIntegrationBundle.message("settings.watch_ddev"));

    public DdevSettingsComponent() {
        final JPanel checkForUpdatesPanel = UI.PanelFactory.panel(this.checkForUpdatesCheckbox).withComment(DdevIntegrationBundle.message("settings.check_for_updates.description")).createPanel();
        final JPanel watchDdevPanel = UI.PanelFactory.panel(this.watchDdevCheckbox).withComment(DdevIntegrationBundle.message("settings.watch_ddev.description")).createPanel();

        this.jPanel = FormBuilder.createFormBuilder()
                .addComponent(checkForUpdatesPanel, 1)
                .addComponent(watchDdevPanel, 1)
                .addComponentFillVertically(new JPanel(), 0)
                .getPanel();
    }

    public @NotNull JPanel getPanel() {
        return this.jPanel;
    }

    public @NotNull JComponent getPreferredFocusedComponent() {
        return this.checkForUpdatesCheckbox;
    }

    public boolean getCheckForUpdatedStatus() {
        return this.checkForUpdatesCheckbox.isSelected();
    }

    public void setCheckForUpdatesStatus(boolean newStatus) {
        this.checkForUpdatesCheckbox.setSelected(newStatus);
    }

    public boolean getWatchDdevCheckboxStatus() {
        return this.watchDdevCheckbox.isSelected();
    }

    public void setWatchDdevCheckboxStatus(boolean newStatus) {
        this.watchDdevCheckbox.setSelected(newStatus);
    }
}

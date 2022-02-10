package de.php_perfect.intellij.ddev.settings;

import com.intellij.ui.components.JBCheckBox;
import com.intellij.util.ui.FormBuilder;
import com.intellij.util.ui.UI;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class DdevSettingsComponent {
    private final @NotNull JPanel jPanel;
    private final @NotNull JBCheckBox checkForUpdatesCheckbox = new JBCheckBox("Check for DDEV updates on startup");
    private final @NotNull JBCheckBox watchDdevCheckbox = new JBCheckBox("Watch DDEV sate in background");

    public DdevSettingsComponent() {
        final JPanel checkForUpdatesPanel = UI.PanelFactory.panel(this.checkForUpdatesCheckbox).withComment("<p>When opening a DDEV enabled project, you will be informed about a new DDEV version, if available.</p>").createPanel();
        final JPanel watchDdevPanel = UI.PanelFactory.panel(this.watchDdevCheckbox).withComment("<p>Required when executing DDEV commands such as Start/Stop on the terminal.</p>").createPanel();

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

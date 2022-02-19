package de.php_perfect.intellij.ddev.statusBar;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.popup.JBPopup;
import com.intellij.openapi.ui.popup.JBPopupFactory;
import com.intellij.openapi.ui.popup.ListPopup;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.util.NlsContexts;
import com.intellij.openapi.wm.CustomStatusBarWidget;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.impl.status.TextPanel;
import com.intellij.openapi.wm.impl.status.widget.StatusBarWidgetWrapper;
import com.intellij.ui.awt.RelativePoint;
import com.intellij.ui.popup.PopupState;
import com.intellij.util.Consumer;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.event.DdevStateChangedNotifier;
import de.php_perfect.intellij.ddev.state.State;
import icons.DdevIntegrationIcons;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;

public class DdevStatusBarWidgetImpl implements CustomStatusBarWidget {
    public static final @NotNull String WIDGET_ID = DdevStatusBarWidgetImpl.class.getName();
    private final @NotNull PopupState<JBPopup> popupState = PopupState.forPopup();
    private final @NotNull Project project;
    private @Nullable StatusBar statusBar;
    private @Nullable TextPanel.WithIconAndArrows component;
    private @Nullable StatusBarWidgetWrapper.StatusBarWidgetClickListener clickListener = null;

    public DdevStatusBarWidgetImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public @NonNls @NotNull String ID() {
        return WIDGET_ID;
    }

    @Override
    public void install(@NotNull StatusBar statusBar) {
        assert statusBar.getProject() == null || statusBar.getProject().equals(this.project) : "Cannot install widget from one project on status bar of another project";
        this.statusBar = statusBar;
        this.clickListener = new StatusBarWidgetWrapper.StatusBarWidgetClickListener(this.getClickConsumer());

        Disposer.register(statusBar, this);
        MessageBus messageBus = ApplicationManager.getApplication().getMessageBus();
        messageBus.connect(this).subscribe(DdevStateChangedNotifier.DDEV_CHANGED, this::updateComponent);
    }

    @Override
    public JComponent getComponent() {
        this.component = new TextPanel.WithIconAndArrows();
        this.component.setIcon(DdevIntegrationIcons.DdevLogoGrey);
        this.component.setToolTipText(DdevIntegrationBundle.message("statusBar.toolTip"));
        this.component.setVisible(false);
        return this.component;
    }

    @Override
    public void dispose() {
        this.statusBar = null;
    }

    private Consumer<MouseEvent> getClickConsumer() {
        return this::showPopup;
    }

    private void showPopup(@NotNull MouseEvent e) {
        if (popupState.isRecentlyHidden()) {
            return;
        }

        DataContext context = DataManager.getInstance().getDataContext((Component) statusBar);
        ListPopup popup = createPopup(context);
        Disposer.register(this, popup);
        Dimension dimension = popup.getContent().getPreferredSize();
        Point at = new Point(0, -dimension.height);
        popupState.prepareToShow(popup);

        popup.show(new RelativePoint(e.getComponent(), at));
    }

    private ListPopup createPopup(DataContext context) {
        ActionGroup group = (ActionGroup) ActionManager.getInstance().getAction("DdevIntegration.Run");

        return JBPopupFactory.getInstance().createActionGroupPopup(null, group, context, JBPopupFactory.ActionSelectionAid.SPEEDSEARCH, false);
    }

    private void updateComponent(State state) {
        if (this.component == null) {
            return;
        }

        if (!state.isConfigured()) {
            this.component.setVisible(false);

            return;
        }

        this.component.setVisible(true);
        this.component.setText(this.getText(state));

        this.clickListener.uninstall(this.component);

        Description description = state.getDescription();
        if (description != null && description.getStatus() == Description.Status.RUNNING) {
            this.clickListener.installOn(this.component, true);
        }
    }

    private @NotNull @NlsContexts.StatusBarText String getText(@NotNull State state) {
        Description description = state.getDescription();
        Description.Status status = Description.Status.UNDEFINED;

        if (description != null) {
            status = description.getStatus();
        }

        return this.buildStatusMessage(status);
    }

    private @NotNull @NlsContexts.StatusBarText String buildStatusMessage(Description.Status status) {
        return DdevIntegrationBundle.message("status." + status);
    }
}

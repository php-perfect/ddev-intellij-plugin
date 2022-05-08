package de.php_perfect.intellij.ddev.statusBar;

import com.intellij.ide.DataManager;
import com.intellij.openapi.actionSystem.ActionGroup;
import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.actionSystem.DataContext;
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
import com.intellij.util.concurrency.EdtExecutorService;
import com.intellij.util.messages.MessageBus;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import de.php_perfect.intellij.ddev.DdevStateChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.icons.DdevIntegrationIcons;
import de.php_perfect.intellij.ddev.state.State;
import de.php_perfect.intellij.ddev.tutorial.GotItTutorial;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.concurrent.TimeUnit;

import static com.intellij.util.ui.update.UiNotifyConnector.doWhenFirstShown;

public class DdevStatusBarWidgetImpl implements CustomStatusBarWidget {
    private static final @NotNull String ACTION_GROUP = "DdevIntegration.Services";
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
        Disposer.register(this.statusBar, this);
        this.clickListener = new StatusBarWidgetWrapper.StatusBarWidgetClickListener(this.getClickConsumer());
        registerUpdateListener();
    }

    @Override
    public JComponent getComponent() {
        this.component = new TextPanel.WithIconAndArrows();
        this.component.setIcon(DdevIntegrationIcons.DdevLogoMono);
        this.component.setToolTipText(DdevIntegrationBundle.message("statusBar.toolTip"));
        this.component.setVisible(false);
        doWhenFirstShown(this.component, () -> delayTutorial(this.component), this);

        return this.component;
    }

    @Override
    public void dispose() {
        this.statusBar = null;
    }

    private void registerUpdateListener() {
        MessageBus messageBus = this.project.getMessageBus();
        messageBus.connect(this).subscribe(DdevStateChangedListener.DDEV_CHANGED, new StatusBarUpdateListener());
    }

    private final class StatusBarUpdateListener implements DdevStateChangedListener {
        @Override
        public void onDdevChanged(State state) {
            DdevStatusBarWidgetImpl.this.updateComponent(state);
        }
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
        ActionGroup group = (ActionGroup) ActionManager.getInstance().getAction(ACTION_GROUP);

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

    private void delayTutorial(@NotNull JComponent component) {
        EdtExecutorService.getScheduledExecutorInstance().schedule(() -> GotItTutorial.getInstance().showStatusBarTutorial(component, this), 3000, TimeUnit.MILLISECONDS);
    }

    private @NotNull @NlsContexts.StatusBarText String getText(@NotNull State state) {
        Description description = state.getDescription();
        Description.Status status = null;

        if (description != null) {
            status = description.getStatus();
        }

        return this.buildStatusMessage(status);
    }

    private @NotNull @NlsContexts.StatusBarText String buildStatusMessage(@Nullable Description.Status status) {
        if (status == null) {
            return DdevIntegrationBundle.message("status.Undefined");
        }

        switch (status) {
            case RUNNING:
                return DdevIntegrationBundle.message("status.Running");
            case STARTING:
                return DdevIntegrationBundle.message("status.Starting");
            case PAUSED:
                return DdevIntegrationBundle.message("status.Paused");
            case STOPPED:
                return DdevIntegrationBundle.message("status.Stopped");
            default:
                return DdevIntegrationBundle.message("status.Undefined");
        }
    }
}

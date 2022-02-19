package de.php_perfect.intellij.ddev.statusBar;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Disposer;
import com.intellij.openapi.wm.StatusBar;
import com.intellij.openapi.wm.StatusBarWidget;
import com.intellij.openapi.wm.StatusBarWidgetFactory;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class DdevStatusBarWidgetFactoryImpl implements StatusBarWidgetFactory {
    @Override
    public @NonNls @NotNull String getId() {
        return DdevStatusBarWidgetImpl.WIDGET_ID;
    }

    @Override
    public @Nls @NotNull String getDisplayName() {
        return DdevIntegrationBundle.message("statusBar.displayName");
    }

    @Override
    public boolean isAvailable(@NotNull Project project) {
        return true;
    }

    @Override
    public @NotNull StatusBarWidget createWidget(@NotNull Project project) {
        return new DdevStatusBarWidgetImpl(project);
    }

    @Override
    public void disposeWidget(@NotNull StatusBarWidget widget) {
        Disposer.dispose(widget);
    }

    @Override
    public boolean canBeEnabledOn(@NotNull StatusBar statusBar) {
        return true;
    }
}

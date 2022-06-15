package de.php_perfect.intellij.ddev.terminal;

import com.intellij.openapi.actionSystem.impl.ActionButton;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ex.ToolWindowManagerListener;
import com.intellij.openapi.wm.impl.InternalDecorator;
import com.intellij.ui.ComponentUtil;
import de.php_perfect.intellij.ddev.tutorial.GotItTutorial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.plugins.terminal.TerminalNewPredefinedSessionAction;
import org.jetbrains.plugins.terminal.TerminalToolWindowFactory;

public final class TutorialListener implements ToolWindowManagerListener {
    public void toolWindowShown(final @NotNull ToolWindow toolWindow) {
        if (!TerminalToolWindowFactory.TOOL_WINDOW_ID.equals(toolWindow.getId())) {
            return;
        }

        final InternalDecorator decorator = ComponentUtil.getParentOfType(InternalDecorator.class, toolWindow.getComponent());

        ComponentUtil.findComponentsOfType(decorator, ActionButton.class).stream()
                .filter(button -> button.getAction() instanceof TerminalNewPredefinedSessionAction)
                .findFirst().ifPresent(button -> GotItTutorial.getInstance().showTerminalTutorial(button, toolWindow.getDisposable()));
    }
}

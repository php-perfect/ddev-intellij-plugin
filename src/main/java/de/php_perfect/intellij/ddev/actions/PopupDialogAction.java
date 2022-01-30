package de.php_perfect.intellij.ddev.actions;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.CommonDataKeys;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.util.NlsActions;
import com.intellij.pom.Navigatable;
import icons.DdevIcons;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class PopupDialogAction extends AnAction {
    public PopupDialogAction(@NotNull @NlsActions.ActionText String text) {
        super(text, null, DdevIcons.Sdk_default_icon);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        StringBuilder dlgMsg = new StringBuilder(e.getPresentation().getText() + " Selected!");
        String dlgTitle = e.getPresentation().getDescription();
        // If an element is selected in the editor, add info about it.
        Navigatable nav = e.getData(CommonDataKeys.NAVIGATABLE);
        if (nav != null) {
            dlgMsg.append(String.format("\nSelected Element: %s", nav.toString()));
        }
        Messages.showMessageDialog(e.getProject(), dlgMsg.toString(), Objects.requireNonNullElse(dlgTitle, ""), Messages.getInformationIcon());
    }
}

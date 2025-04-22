package de.php_perfect.intellij.ddev.actions;

import com.intellij.icons.AllIcons;
import com.intellij.ide.BrowserUtil;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.DumbAwareAction;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;

public final class ReportIssueAction extends DumbAwareAction {
    private static final String NEW_ISSUE_URL = "https://github.com/ddev/ddev-intellij-plugin/issues/new?assignees=&labels=bug&template=bug_report.yml";

    public ReportIssueAction() {
        super(DdevIntegrationBundle.messagePointer("action.DdevIntegration.ReportIssue.text"), DdevIntegrationBundle.messagePointer("action.DdevIntegration.ReportIssue.description"), AllIcons.Vcs.Vendors.Github);
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        BrowserUtil.browse(NEW_ISSUE_URL);
    }
}

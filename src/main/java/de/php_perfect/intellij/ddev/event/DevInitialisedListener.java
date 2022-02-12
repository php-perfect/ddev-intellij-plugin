package de.php_perfect.intellij.ddev.event;

import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;

public final class DevInitialisedListener implements ChangeActionNotifier {
    private static final ExtensionPointName<DdevAwareActivity> EP_NAME =
            ExtensionPointName.create("de.php_perfect.intellij.ddev.ddevAwareActivity");

    @Override
    public void onDdevInit(Project project) {
        for (DdevAwareActivity extension : EP_NAME.getExtensionList()) {
            extension.runActivity(project);
        }
    }
}

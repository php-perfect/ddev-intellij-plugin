package de.php_perfect.intellij.ddev.event;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.extensions.ExtensionPointName;
import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DdevAwareActivity;
import de.php_perfect.intellij.ddev.DdevInitialisedListener;

// todo: Tidy and move something more domain related
public final class DdevInitialisedActivitiesListener implements DdevInitialisedListener {
    private static final ExtensionPointName<DdevAwareActivity> EP_NAME = ExtensionPointName.create("de.php_perfect.intellij.ddev.ddevAwareActivity");

    @Override
    public void onDdevInitialised(Project project) {
        for (DdevAwareActivity extension : EP_NAME.getExtensionList()) {
            ApplicationManager.getApplication().executeOnPooledThread(() -> extension.runActivity(project));
        }
    }
}

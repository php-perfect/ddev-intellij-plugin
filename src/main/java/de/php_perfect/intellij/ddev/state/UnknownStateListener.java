package de.php_perfect.intellij.ddev.state;

import com.intellij.openapi.project.Project;
import de.php_perfect.intellij.ddev.DescriptionChangedListener;
import de.php_perfect.intellij.ddev.cmd.Description;
import de.php_perfect.intellij.ddev.notification.DdevNotifier;
import org.jetbrains.annotations.Nullable;

public final class UnknownStateListener implements DescriptionChangedListener {
    private final Project project;

    public UnknownStateListener(Project project) {
        this.project = project;
    }

    @Override
    public void onDescriptionChanged(@Nullable Description description) {
        System.out.println(description);
        if (description == null || description.getStatus() == null) {
            DdevNotifier.getInstance(this.project).asyncNotifyUnknownStateEntered();
        }
    }
}

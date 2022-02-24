package de.php_perfect.intellij.ddev;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface DdevInitialisedListener {
    Topic<DdevInitialisedListener> DDEV_INITIALISED = Topic.create("DDEV initialised", DdevInitialisedListener.class);

    void onDdevInitialised(Project project);
}
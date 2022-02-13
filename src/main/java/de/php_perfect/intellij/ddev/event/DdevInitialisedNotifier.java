package de.php_perfect.intellij.ddev.event;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface DdevInitialisedNotifier {

    Topic<DdevInitialisedNotifier> DDEV_INITIALISED = Topic.create("DDEV initialised", DdevInitialisedNotifier.class);

    void onDdevInitialised(Project project);
}
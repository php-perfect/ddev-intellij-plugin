package de.php_perfect.intellij.ddev.event;

import com.intellij.openapi.project.Project;
import com.intellij.util.messages.Topic;

public interface ChangeActionNotifier {

    Topic<ChangeActionNotifier> CHANGE_ACTION_TOPIC = Topic.create("DDEV initialised", ChangeActionNotifier.class);

    void onDdevInit(Project project);
}
package de.php_perfect.intellij.ddev.event;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.state.State;

public interface DdevStateChangedNotifier {
    Topic<DdevStateChangedNotifier> DDEV_CHANGED = Topic.create("DDEV changed", DdevStateChangedNotifier.class);

    void onDdevChanged(State state);
}

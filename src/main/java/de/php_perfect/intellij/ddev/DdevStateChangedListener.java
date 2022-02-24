package de.php_perfect.intellij.ddev;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.state.State;

public interface DdevStateChangedListener {
    Topic<DdevStateChangedListener> DDEV_CHANGED = Topic.create("DDEV status changed", DdevStateChangedListener.class);

    void onDdevChanged(State state);
}

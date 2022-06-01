package de.php_perfect.intellij.ddev;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public interface StateChangedListener {
    Topic<StateChangedListener> DDEV_CHANGED = Topic.create("DDEV state changed", StateChangedListener.class);

    void onDdevChanged(@NotNull State state);
}

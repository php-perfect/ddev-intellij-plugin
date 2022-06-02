package de.php_perfect.intellij.ddev;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.state.State;
import org.jetbrains.annotations.NotNull;

public interface StateInitializedListener {
    Topic<StateInitializedListener> STATE_INITIALIZED = Topic.create("DDEV state initialized", StateInitializedListener.class);

    void onStateInitialized(@NotNull State state);
}

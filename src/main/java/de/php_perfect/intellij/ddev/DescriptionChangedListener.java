package de.php_perfect.intellij.ddev;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.cmd.Description;
import org.jetbrains.annotations.Nullable;

public interface DescriptionChangedListener {
    Topic<DescriptionChangedListener> DESCRIPTION_CHANGED = Topic.create("DDEV description changed", DescriptionChangedListener.class);

    void onDescriptionChanged(@Nullable Description description);
}

package de.php_perfect.intellij.ddev;

import com.intellij.util.messages.Topic;
import de.php_perfect.intellij.ddev.cmd.DatabaseInfo;
import org.jetbrains.annotations.Nullable;

public interface DatabaseInfoChangedListener {
    Topic<DatabaseInfoChangedListener> DATABASE_INFO_CHANGED_TOPIC = Topic.create("DDEV Database Info Changed", DatabaseInfoChangedListener.class);

    void onDatabaseInfoChanged(@Nullable DatabaseInfo databaseInfo);
}

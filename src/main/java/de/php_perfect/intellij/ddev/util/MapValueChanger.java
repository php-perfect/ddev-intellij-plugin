package de.php_perfect.intellij.ddev.util;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.BiConsumer;

public final class MapValueChanger {
    public static <K, V> void apply(Map<K, V> currentMap, Map<K, V> newMap) {
        apply(currentMap, newMap, null, null);
    }

    public static <K, V> void apply(Map<K, V> currentMap, Map<K, V> newMap, @Nullable BiConsumer<K, V> onRemoved, @Nullable BiConsumer<K, V> onAdded) {
        MapDifference<K, V> diff = Maps.difference(currentMap, newMap);

        for (Map.Entry<K, V> removed : diff.entriesOnlyOnLeft().entrySet()) {
            currentMap.remove(removed.getKey());
            if (onRemoved != null) {
                onRemoved.accept(removed.getKey(), removed.getValue());
            }
        }

        for (Map.Entry<K, V> added : diff.entriesOnlyOnRight().entrySet()) {
            currentMap.put(added.getKey(), added.getValue());
            if (onAdded != null) {
                onAdded.accept(added.getKey(), added.getValue());
            }
        }
    }
}

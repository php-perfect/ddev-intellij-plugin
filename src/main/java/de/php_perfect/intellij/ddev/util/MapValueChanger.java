package de.php_perfect.intellij.ddev.util;

import com.google.common.collect.MapDifference;
import com.google.common.collect.Maps;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

public final class MapValueChanger {
    public static <K, V> void apply(final Map<K, V> currentMap, final Map<K, V> newMap) {
        apply(currentMap, newMap, (a, b) -> {}, (a, b) -> {});
    }

    public static <K, V> void apply(Map<K, V> currentMap, Map<K, V> newMap, BiConsumer<K, V> onRemoved, BiConsumer<K, V> onAdded) {
        MapDifference<K, V> diff = Maps.difference(currentMap, newMap);

        for (Map.Entry<K, V> removed : diff.entriesOnlyOnLeft().entrySet()) {
            currentMap.remove(removed.getKey());
            onRemoved.accept(removed.getKey(), removed.getValue());
        }

        for (Map.Entry<K, V> added : diff.entriesOnlyOnRight().entrySet()) {
            currentMap.put(added.getKey(), added.getValue());
            onAdded.accept(added.getKey(), added.getValue());
        }
    }
}

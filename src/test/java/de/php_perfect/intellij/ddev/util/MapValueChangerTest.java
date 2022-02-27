package de.php_perfect.intellij.ddev.util;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

final class MapValueChangerTest {
    @Test
    void applyOnEmptyMap() {
        Map<String, TestAction> currentMap = new HashMap<>();
        Map<String, TestAction> newMap = new HashMap<>();

        newMap.put("action.a", new TestAction());

        MapValueChanger.apply(currentMap, newMap);

        assertTrue(currentMap.containsKey("action.a"));
    }

    @Test
    void applyOnPrefilledMap() {
        Map<String, TestAction> currentMap = new HashMap<>();
        Map<String, TestAction> newMap = new HashMap<>();

        currentMap.put("action.a", new TestAction());
        currentMap.put("action.b", new TestAction());

        newMap.put("action.b", new TestAction());
        newMap.put("action.c", new TestAction());

        MapValueChanger.apply(currentMap, newMap, (String key, TestAction b) -> assertEquals("action.a", key), (String key, TestAction b) -> assertEquals("action.c", key));

        assertTrue(currentMap.containsKey("action.c"));
        assertFalse(currentMap.containsKey("action.a"));
    }
}

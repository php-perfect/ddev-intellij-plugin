package de.php_perfect.intellij.ddev.index;

import com.intellij.ide.util.PropertiesComponent;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class ManagedConfigurationIndexImpl implements ManagedConfigurationIndex {
    private static final @NotNull String NAMESPACE = "de.php_perfect.intellij.ddev.";
    private static final @NotNull String HASH_SUFFIX = ".hash";
    private final @NotNull Project project;

    public ManagedConfigurationIndexImpl(@NotNull Project project) {
        this.project = project;
    }

    @Override
    public void set(@NonNls @NotNull String id, @NotNull Class<?> type, int hash) {
        this.setManagedConfiguration(id, type.getName(), Integer.toHexString(hash));
    }

    private void setManagedConfiguration(@NonNls @NotNull String id, @NonNls @NotNull String type, @NonNls @NotNull String hash) {
        final String key = buildKey(type);
        final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(this.project);

        propertiesComponent.setValue(key, id);
        propertiesComponent.setValue(buildHashKey(key), hash);
    }

    @Override
    public boolean isManaged(@NonNls @NotNull String id, @NotNull Class<?> type) {
        return this.isManagedConfiguration(id, type.getName());
    }

    private boolean isManagedConfiguration(@NonNls @NotNull String id, @NonNls @NotNull String type) {
        final String key = buildKey(type);
        final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(this.project);

        if (!propertiesComponent.isValueSet(key)) {
            return false;
        }

        return id.equals(propertiesComponent.getValue(key));
    }

    @Override
    public boolean isUpToDate(@NotNull Class<?> type, int hash) {
        return this.isUpToDate(type.getName(), Integer.toHexString(hash));
    }

    private boolean isUpToDate(@NonNls @NotNull String type, @NonNls @NotNull String hash) {
        final String key = buildKey(type);
        final String hashKey = buildHashKey(key);
        final PropertiesComponent propertiesComponent = PropertiesComponent.getInstance(this.project);

        return propertiesComponent.isValueSet(key) &&
                propertiesComponent.isValueSet(hashKey) &&
                propertiesComponent.getValue(hashKey, "").equals(hash);
    }

    @Override
    public void remove(@NotNull Class<?> type) {
        this.removeManagedConfiguration(type.getName());
    }

    private void removeManagedConfiguration(@NonNls @NotNull String type) {
        final String key = buildKey(type);
        final String hashKey = buildHashKey(key);

        PropertiesComponent.getInstance(this.project).unsetValue(key);
        PropertiesComponent.getInstance(this.project).unsetValue(hashKey);
    }

    @NotNull
    private static String buildKey(@NonNls @NotNull String type) {
        return NAMESPACE + type;
    }

    @NotNull
    private static String buildHashKey(@NonNls @NotNull String key) {
        return key + HASH_SUFFIX;
    }
}

package de.php_perfect.intellij.ddev.config;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.jetbrains.jsonSchema.extension.JsonSchemaFileProvider;
import com.jetbrains.jsonSchema.extension.JsonSchemaProviderFactory;
import com.jetbrains.jsonSchema.extension.SchemaType;
import de.php_perfect.intellij.ddev.DdevIntegrationBundle;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collections;
import java.util.List;

public final class ConfigJsonSchemaProviderFactory implements JsonSchemaProviderFactory {
    private static final String DDEV_CONFIG_YAML = ".ddev/config.yaml";

    @Override
    public @NotNull List<JsonSchemaFileProvider> getProviders(@NotNull Project project) {
        return Collections.singletonList(new JsonSchemaFileProvider() {
            @Override
            public boolean isAvailable(@NotNull VirtualFile file) {
                return isDdevConfig(file);
            }

            @Override
            public @NotNull String getName() {
                return DdevIntegrationBundle.message("ddev.configuration");
            }


            @Override
            public @Nullable VirtualFile getSchemaFile() {
                return JsonSchemaProviderFactory.getResourceFile(getClass(), "/schema/config.schema.json");
            }

            @Override
            public @NotNull SchemaType getSchemaType() {
                return SchemaType.embeddedSchema;
            }
        });
    }

    private static boolean isDdevConfig(@NotNull VirtualFile file) {
        return file.getPath().endsWith(DDEV_CONFIG_YAML);
    }
}

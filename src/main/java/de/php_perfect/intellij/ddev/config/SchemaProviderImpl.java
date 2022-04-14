package de.php_perfect.intellij.ddev.config;

import com.intellij.json.JsonFileType;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.testFramework.LightVirtualFile;
import com.intellij.util.LocalTimeCounter;
import com.intellij.util.io.HttpRequests;
import com.intellij.util.io.RequestBuilder;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public final class SchemaProviderImpl implements SchemaProvider {
    private static final Logger LOG = Logger.getInstance(SchemaProviderImpl.class);
    private static final @NonNls String FALLBACK_VERSION = "master";
    private static final @NonNls String SCHEMA_URL = "https://raw.githubusercontent.com/drud/ddev/%s/markdown-link-check.json";
    private static final @NonNls String SCHEMA_NAME = "ddev-config-%s.schema.json";

    private @Nullable VirtualFile schema = null;

    @Override
    public @Nullable VirtualFile getSchema() {
        return this.schema;
    }

    @Override
    public void loadSchema(@NotNull String version) {
        String schema = this.requestSchema(version);

        if (schema == null) {
            return;
        }

        this.schema = buildFile(schema, version);
    }

    private @NotNull VirtualFile buildFile(@NotNull String content, @NotNull String version) {
        String name = String.format(SCHEMA_NAME, version);

        return new LightVirtualFile(name, JsonFileType.INSTANCE, content, StandardCharsets.UTF_8, LocalTimeCounter.currentTime());
    }

    private @Nullable String requestSchema(String version) {
        String url = this.buildUrl(version);
        LOG.debug(String.format("Loading DDEV config schema for version %s", version));

        try {
            return request(url);
        } catch (IOException ignored) {
            url = this.buildUrl(FALLBACK_VERSION);

            try {
                LOG.info(String.format("Could not load DDEV schema from url %s, %s is used as fallback", url, FALLBACK_VERSION));
                return request(url);
            } catch (IOException e) {
                LOG.error(String.format("Could not load DDEV schema from url %s", url), e);
                return null;
            }
        }
    }

    private @NotNull String buildUrl(String version) {
        return String.format(SCHEMA_URL, version);
    }

    private @NotNull String request(String url) throws IOException {
        final RequestBuilder requestBuilder = HttpRequests.request(url).forceHttps(true);
        return requestBuilder.readString();
    }
}

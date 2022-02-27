package de.php_perfect.intellij.ddev.version;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.Service;
import com.intellij.openapi.progress.ProgressIndicator;
import com.intellij.util.io.HttpRequests;
import com.intellij.util.io.RequestBuilder;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

@Service(Service.Level.APP)
public final class GithubClient {
    private final static @NotNull String RELEASE_URL = "https://github.com/drud/ddev/releases/latest";

    public LatestRelease loadCurrentVersion(@NotNull ProgressIndicator indicator) throws IOException {
        final RequestBuilder requestBuilder = HttpRequests.request(RELEASE_URL).accept("application/json").redirectLimit(1);
        indicator.checkCanceled();
        return createParser().fromJson(requestBuilder.readString(indicator), LatestRelease.class);
    }

    private Gson createParser() {
        return new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
    }

    public static GithubClient getInstance() {
        return ApplicationManager.getApplication().getService(GithubClient.class);
    }
}

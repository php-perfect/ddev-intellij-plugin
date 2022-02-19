package de.php_perfect.intellij.ddev.cmd;

import org.jetbrains.annotations.Nullable;

public class Service {
    private @Nullable String fullName;

    private @Nullable String httpUrl;

    private @Nullable String httpsUrl;

    public void setFullName(@Nullable String fullName) {
        this.fullName = fullName;
    }

    public void setHttpUrl(@Nullable String httpUrl) {
        this.httpUrl = httpUrl;
    }

    public void setHttpsUrl(@Nullable String httpsUrl) {
        this.httpsUrl = httpsUrl;
    }

    public @Nullable String getFullName() {
        return fullName;
    }

    public @Nullable String getHttpUrl() {
        return httpUrl;
    }

    public @Nullable String getHttpsUrl() {
        return httpsUrl;
    }
}

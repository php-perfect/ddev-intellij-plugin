package de.php_perfect.intellij.ddev.cmd;

import com.google.gson.annotations.SerializedName;

public class Versions {
    @SerializedName("DDEV version")
    private String ddevVersion;

    public String getDdevVersion() {
        return ddevVersion;
    }

    public void setDdevVersion(String ddevVersion) {
        this.ddevVersion = ddevVersion;
    }
}

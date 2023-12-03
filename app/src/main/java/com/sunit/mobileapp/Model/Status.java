package com.sunit.mobileapp.Model;

import com.google.gson.annotations.SerializedName;

public class Status {
    @SerializedName("version")
    public String version;
    @SerializedName("authServerUrl")
    public String authServerUrl;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getAuthServerUrl() {
        return authServerUrl;
    }

    public void setAuthServerUrl(String authServerUrl) {
        this.authServerUrl = authServerUrl;
    }
}

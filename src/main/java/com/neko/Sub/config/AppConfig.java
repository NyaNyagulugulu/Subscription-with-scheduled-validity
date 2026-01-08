package com.neko.Sub.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "app")
public class AppConfig {
    private String backendUrl;
    private int invalidTimeMinutes;

    public String getBackendUrl() {
        return backendUrl;
    }

    public void setBackendUrl(String backendUrl) {
        this.backendUrl = backendUrl;
    }

    public int getInvalidTimeMinutes() {
        return invalidTimeMinutes;
    }

    public void setInvalidTimeMinutes(int invalidTimeMinutes) {
        this.invalidTimeMinutes = invalidTimeMinutes;
    }
}
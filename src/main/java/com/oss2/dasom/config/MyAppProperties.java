package com.oss2.dasom.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@ConfigurationProperties("myapp")
public class MyAppProperties {
    private String api_key;

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }
}

package com.point.fpi.application.configuration.request;

import com.point.fpi.domain.configuration.param.ConfigurationAddParam;
import com.point.fpi.domain.configuration.param.ConfigurationModifyParam;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ConfigurationRequest {
    @NotBlank
    private String configurationKey;
    @NotBlank
    private String configurationValue;

    public ConfigurationAddParam toAddParam() {
        return ConfigurationAddParam.builder()
                .configurationKey(configurationKey)
                .configurationValue(configurationValue)
                .build();
    }

    public ConfigurationModifyParam toModifyParam() {
        return ConfigurationModifyParam.builder()
                .configurationValue(configurationValue)
                .build();
    }
}

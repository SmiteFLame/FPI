package com.point.fpi.domain.configuration.param;

import com.point.fpi.domain.configuration.entity.Configuration;
import lombok.Builder;

@Builder
public class ConfigurationAddParam {
    private String configurationKey;
    private String configurationValue;

    public Configuration to() {
        return new Configuration(
               this.configurationKey,
                this.configurationValue
        );
    }
}

package com.point.fpi.domain.configuration.param;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class ConfigurationModifyParam {
    private String configurationValue;
}

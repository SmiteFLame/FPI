package com.point.fpi.application.configuration;

import com.point.fpi.application.configuration.request.ConfigurationRequest;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.configuration.entity.Configuration;
import com.point.fpi.domain.configuration.service.ConfigurationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfigurationApplication {
    private final ConfigurationService configurationService;

    public void addConfiguration(
            ConfigurationRequest request
    ) {
        if (configurationService.checkConfigurationByConfigurationKey(request.getConfigurationKey())) {
            throw new BizException("이미 같은 Configuration Key 존재합니다.");
        }
        configurationService.addConfiguration(request.toAddParam());
    }

    public void modifyConfiguration(
            ConfigurationRequest request
    ) {
        Configuration configuration = configurationService.getByConfigurationKey(request.getConfigurationKey());
        configurationService.modifyConfiguration(configuration, request.toModifyParam());
    }
}
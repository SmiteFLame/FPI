package com.point.fpi.domain.configuration.service;

import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.configuration.entity.Configuration;
import com.point.fpi.domain.configuration.param.ConfigurationAddParam;
import com.point.fpi.domain.configuration.param.ConfigurationModifyParam;
import com.point.fpi.domain.configuration.repository.ConfigurationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ConfigurationService {
    private final ConfigurationRepository configurationRepository;

    public Configuration getByConfigurationKey(
            String configurationKey
    ) {
        return configurationRepository.findByConfigurationKey(configurationKey)
                .orElseThrow(() -> new BizException("Configuration 이 존재하지 않습니다."));
    }

    public boolean checkConfigurationByConfigurationKey(
            String configurationKey
    ) {
        return configurationRepository.existsByConfigurationKey(configurationKey);
    }

    public void addConfiguration(
            ConfigurationAddParam param
    ) {
        configurationRepository.save(param.to());
    }

    public void modifyConfiguration(
            Configuration configuration,
            ConfigurationModifyParam param
    ) {
        configuration.modifyConfigurationValue(param.getConfigurationValue());
        configurationRepository.save(configuration);
    }
}

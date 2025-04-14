package com.point.fpi.domain.configuration.repository;

import com.point.fpi.domain.configuration.entity.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ConfigurationRepository extends JpaRepository<Configuration, Long> {
    Boolean existsByConfigurationKey(String configurationKey);
    Optional<Configuration> findByConfigurationKey(String configurationKey);
}

package com.point.fpi.domain.configuration.entity;

import com.point.fpi.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.Getter;

@Getter
@Entity
@Table(name = "configuration")
public class Configuration extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "configuration_id")
    private Long pointId;

    @Column(name = "configuration_key", nullable = false, unique = true)
    private String configurationKey;

    @Column(name = "configuration_value", nullable = false)
    private String configurationValue;

    public Configuration(
            String configurationKey,
            String configurationValue
    ) {
        this.configurationKey = configurationKey;
        this.configurationValue = configurationValue;
    }

    public void modifyConfigurationValue(
            String configurationValue
    ) {
        this.configurationValue = configurationValue;
    }
}

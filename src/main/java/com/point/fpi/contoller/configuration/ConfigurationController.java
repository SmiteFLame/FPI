package com.point.fpi.contoller.configuration;

import com.point.fpi.application.configuration.ConfigurationApplication;
import com.point.fpi.application.configuration.request.ConfigurationRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@Validated
@RequestMapping("/configuration")
@RequiredArgsConstructor
public class ConfigurationController {
    private final ConfigurationApplication configurationApplication;

    @PostMapping
    public void addConfiguration(
            @RequestBody @Valid ConfigurationRequest request
    ) {
        configurationApplication.addConfiguration(request);
    }

    @PutMapping
    public void updateConfiguration(
            @RequestBody @Valid ConfigurationRequest request
    ) {
        configurationApplication.modifyConfiguration(request);
    }
}

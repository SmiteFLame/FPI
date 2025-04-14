package com.point.fpi.contoller.point;

import com.point.fpi.application.point.PointApplication;
import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.application.point.request.PointCancelRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@Validated
@RequestMapping("/point")
@RequiredArgsConstructor
public class PointController {
    private final PointApplication pointApplication;

    @PostMapping
    public void addPoint(
            @RequestBody @Valid PointAddRequest request
    ) {
        pointApplication.addPoint(request);
    }

    @PostMapping
    public void cancelPoint(
            @RequestBody @Valid PointCancelRequest request
    ) {
        pointApplication.cancelPoint(request);
    }
}

package com.point.fpi.contoller.point;

import com.point.fpi.application.point.PointApplication;
import com.point.fpi.application.point.request.PointAddRequest;
import com.point.fpi.application.point.request.PointCancelRequest;
import com.point.fpi.application.point.request.PointUseCancelRequest;
import com.point.fpi.application.point.request.PointUseRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


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

    @DeleteMapping
    public void cancelPoint(
            @RequestBody @Valid PointCancelRequest request
    ) {
        pointApplication.cancelPoint(request);
    }

    @PostMapping("/use")
    public void userPoint(
            @RequestBody @Valid PointUseRequest request
    ) {
        pointApplication.usePoint(request);
    }

    @DeleteMapping("/use")
    public void cancelUsePoint(
            @RequestBody @Valid PointUseCancelRequest request
    ) {
        pointApplication.cancelUsePoint(request);
    }
}

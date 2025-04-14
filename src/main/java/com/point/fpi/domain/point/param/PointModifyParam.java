package com.point.fpi.domain.point.param;

import com.point.fpi.domain.point.entity.Point;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PointModifyParam {
    private Point point;
    private Long pointAmount;
}

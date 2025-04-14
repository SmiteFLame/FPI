package com.point.fpi.domain.point.service;

import com.point.fpi.common.enums.PointState;
import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.point.entity.Point;
import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.param.PointAddParam;
import com.point.fpi.domain.point.repository.PointRepository;
import com.point.fpi.domain.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;

    public Point getPointByPointKey(
            String pointKey
    ) {
        return pointRepository.findPointByPointKey(pointKey)
                .orElseThrow(() -> new BizException("존재하지 않는 포인트 입니다."));
    }

    public List<Point> listByPointByUserAndPointStateNotIn(
            User user,
            List<PointState> pointStateList
    ) {
        return pointRepository.findAllByUserAndPointStateNotIn(user, pointStateList);
    }

    public void expiredAllPoint(
            List<Point> pointList
    ) {
        pointList.forEach(Point::expirePoint);
        pointRepository.saveAll(pointList);
    }

    public Point addPoint(
            PointAddParam param
    ) {
        return pointRepository.save(param.to());
    }

    public void cancelPoint(
            Point point
    ) {
        point.cancelPoint();
        pointRepository.save(point);
    }
}

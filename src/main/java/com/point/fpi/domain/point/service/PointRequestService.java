package com.point.fpi.domain.point.service;

import com.point.fpi.common.exception.BizException;
import com.point.fpi.domain.point.entity.PointRequest;
import com.point.fpi.domain.point.param.PointRequestAddParam;
import com.point.fpi.domain.point.repository.PointRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
public class PointRequestService {
    private final PointRequestRepository pointRequestRepository;

    public PointRequest getPointRequestByOrderId(
            String orderId
    ) {
        return pointRequestRepository.findByOrderId(orderId)
                .orElseThrow(() -> new BizException("포인트 주문이 존재하지 않습니다."));
    }

    public PointRequest addPointRequest(
            PointRequestAddParam param
    ) {
        return pointRequestRepository.save(param.to());
    }
}

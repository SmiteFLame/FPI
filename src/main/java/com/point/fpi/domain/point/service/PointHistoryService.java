package com.point.fpi.domain.point.service;

import com.point.fpi.domain.point.entity.PointHistory;
import com.point.fpi.domain.point.param.PointHistoryAddParam;
import com.point.fpi.domain.point.repository.PointHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PointHistoryService {
    private final PointHistoryRepository pointHistoryRepository;

    public void addPointHistory(
            PointHistoryAddParam param
    ) {
        pointHistoryRepository.save(param.to());
    }

    public void addAllPointHistory(
            List<PointHistoryAddParam> paramList
    ) {
        pointHistoryRepository.saveAll(paramList
                .stream().map(PointHistoryAddParam::to).toList()
        );
    }
}

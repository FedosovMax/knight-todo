package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.exception.DayNotFoundException;
import com.knighttodo.knighttodo.gateway.DayGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayMapper;
import com.knighttodo.knighttodo.service.DayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class DayServiceImpl implements DayService {

    private final DayGateway dayGateway;

    @Override
    @Transactional
    public DayVO save(DayVO dayVO) {
        return dayGateway.save(dayVO);
    }

    @Override
    public List<DayVO> findAll() {
        return dayGateway.findAll();
    }

    @Override
    public DayVO findById(UUID dayId) {
        return dayGateway.findById(dayId).orElseThrow(() -> {
            log.error(String.format("Day with such id:%s can't be " + "found", dayId));
            return new DayNotFoundException(String.format("Day with such id:%s can't be " + "found", dayId));
        });
    }

    @Override
    @Transactional
    public DayVO updateDay(UUID dayId, DayVO changedDayVO) {
        DayVO dayVO = findById(dayId);
        dayVO.setDayName(changedDayVO.getDayName());
        return dayGateway.save(dayVO);
    }

    @Override
    @Transactional
    public void deleteById(UUID dayId) {
        dayGateway.deleteAllDayTodos(dayId);
        dayGateway.deleteById(dayId);
    }
}

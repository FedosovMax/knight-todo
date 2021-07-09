package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.exception.DayNotFoundException;
import com.knighttodo.knighttodo.gateway.DayGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayMapper;
import com.knighttodo.knighttodo.service.DayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class DayServiceImpl implements DayService {

    private final DayGateway dayGateway;
    private final DayMapper dayMapper;

    @Override
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
    public DayVO updateDay(UUID dayId, DayVO changedDayVO) {
        changedDayVO.setId(dayId);
        DayVO dayVO = findById(dayMapper.toDay(changedDayVO).getId());
        dayVO.setDayName(changedDayVO.getDayName());
        return dayGateway.save(dayVO);
    }

    @Override
    public void deleteById(UUID dayId) {
        dayGateway.deleteById(dayId);
    }
}

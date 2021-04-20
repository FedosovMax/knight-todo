package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.exception.DayNotFoundException;
import com.knighttodo.knighttodo.gateway.DayGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayMapper;
import com.knighttodo.knighttodo.service.DayService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
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
    public DayVO findById(String dayId) {
        return dayGateway.findById(dayId)
            .orElseThrow(() -> new DayNotFoundException(
                String.format("Day with such id:%s can't be " + "found", dayId)));
    }

    @Override
    public DayVO updateDay(String dayId, DayVO changedDayVO) {
        changedDayVO.setId(dayId);
        DayVO dayVO = dayGateway.findById(dayMapper.toDay(changedDayVO).getId())
            .orElseThrow(() -> new DayNotFoundException(
                String.format("Day with such id:%s is not found", dayId)));

        dayVO.setDayName(changedDayVO.getDayName());
        return dayGateway.save(dayVO);
    }

    @Override
    public void deleteById(String dayId) {
        dayGateway.deleteById(dayId);
    }
}

package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.DayVO;
import com.knighttodo.todocore.exception.DayNotFoundException;
import com.knighttodo.todocore.gateway.DayGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class DayService {

    private final DayGateway dayGateway;

    @Transactional
    public DayVO save(DayVO dayVO) {
        return dayGateway.save(dayVO);
    }

    public List<DayVO> findAll() {
        return dayGateway.findAll();
    }

    public DayVO findById(UUID dayId) {
        return dayGateway.findById(dayId).orElseThrow(() -> {
            log.error(String.format("Day with such id:%s can't be " + "found", dayId));
            return new DayNotFoundException(String.format("Day with such id:%s can't be " + "found", dayId));
        });
    }

    @Transactional
    public DayVO updateDay(UUID dayId, DayVO changedDayVO) {
        DayVO dayVO = findById(dayId);
        dayVO.setDayName(changedDayVO.getDayName());
        return dayGateway.save(dayVO);
    }

    @Transactional
    public void deleteById(UUID dayId) {
        dayGateway.deleteAllDayTodos(dayId);
        dayGateway.deleteById(dayId);
    }
}

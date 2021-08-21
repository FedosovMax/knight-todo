package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.exception.DayTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.gateway.DayTodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.DayService;
import com.knighttodo.knighttodo.service.DayTodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class DayTodoServiceImpl implements DayTodoService {

    private final DayService dayService;
    private final DayTodoGateway dayTodoGateway;
    private final ExperienceGateway experienceGateway;

    @Override
    @Transactional
    public DayTodoVO save(UUID dayId, DayTodoVO dayTodoVO) {
        dayTodoVO.setDay(dayService.findById(dayId));
        return dayTodoGateway.save(dayTodoVO);
    }

    @Override
    public List<DayTodoVO> findAll() {
        return dayTodoGateway.findAll();
    }

    @Override
    public DayTodoVO findById(UUID dayTodoId) {
        return dayTodoGateway.findById(dayTodoId)
                .orElseThrow(() -> {
                    log.error(String.format("Day Todo with such id:%s can't be found", dayTodoId));
                    return new DayTodoNotFoundException(String.format("Day Todo with such id:%s can't be found", dayTodoId));
                });
    }

    @Override
    @Transactional
    public DayTodoVO updateDayTodo(UUID dayTodoId, DayTodoVO changedDayTodoVO) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        checkUpdatePossibility(dayTodoVO, changedDayTodoVO);

        dayTodoVO.setDayTodoName(changedDayTodoVO.getDayTodoName());
        dayTodoVO.setScariness(changedDayTodoVO.getScariness());
        dayTodoVO.setHardness(changedDayTodoVO.getHardness());
        return dayTodoGateway.save(dayTodoVO);
    }

    private void checkUpdatePossibility(DayTodoVO dayTodoVO, DayTodoVO changedDayTodoVO) {
        if (dayTodoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(dayTodoVO, changedDayTodoVO)) {
            log.error("Can not update day Todo's field in ready state");
            throw new UnchangeableFieldUpdateException("Can not update day Todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(DayTodoVO dayTodoVO, DayTodoVO changedDayTodoVO) {
        return !dayTodoVO.getScariness().equals(changedDayTodoVO.getScariness())
                || !dayTodoVO.getHardness().equals(changedDayTodoVO.getHardness());
    }

    @Override
    @Transactional
    public void deleteById(UUID dayTodoId) {
        dayTodoGateway.deleteById(dayTodoId);
    }

    @Override
    public List<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoGateway.findByDayId(dayId);
    }

    @Override
    @Transactional
    public DayTodoVO updateIsReady(UUID dayId, UUID dayTodoId, boolean isReady) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        dayTodoVO.setDay(dayService.findById(dayId));
        dayTodoVO.setReady(isReady);
        dayTodoVO = dayTodoGateway.save(dayTodoVO);
        return experienceGateway.calculateExperience(dayTodoVO);
    }
}


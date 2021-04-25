package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.exception.DayTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.gateway.DayTodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.DayService;
import com.knighttodo.knighttodo.service.DayTodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DayTodoServiceImpl implements DayTodoService {

    private final DayService dayService;
    private final DayTodoGateway dayTodoGateway;
    private final ExperienceGateway experienceGateway;

    @Override
    public DayTodoVO save(String dayId, DayTodoVO dayTodoVO) {
        dayTodoVO.setDay(dayService.findById(dayId));
        return dayTodoGateway.save(dayTodoVO);
    }

    @Override
    public List<DayTodoVO> findAll() {
        return dayTodoGateway.findAll();
    }

    @Override
    public DayTodoVO findById(String dayTodoId) {
        return dayTodoGateway.findById(dayTodoId)
            .orElseThrow(() -> new DayTodoNotFoundException(String.format("Day Todo with such id:%s can't be found", dayTodoId)));
    }

    @Override
    public DayTodoVO updateDayTodo(String dayTodoId, DayTodoVO changedDayTodoVO) {
        DayTodoVO dayTodoVO = dayTodoGateway.findById(dayTodoId)
            .orElseThrow(() -> new DayTodoNotFoundException(String.format("Day Todo with such id:%s can't be found", dayTodoId)));

        checkUpdatePossibility(dayTodoVO, changedDayTodoVO);

        dayTodoVO.setDayTodoName(changedDayTodoVO.getDayTodoName());
        dayTodoVO.setScariness(changedDayTodoVO.getScariness());
        dayTodoVO.setHardness(changedDayTodoVO.getHardness());
        return dayTodoGateway.save(dayTodoVO);
    }

    private void checkUpdatePossibility(DayTodoVO dayTodoVO, DayTodoVO changedDayTodoVO) {
        if (dayTodoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(dayTodoVO, changedDayTodoVO)) {
            throw new UnchangeableFieldUpdateException("Can not update day todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(DayTodoVO dayTodoVO, DayTodoVO changedDayTodoVO) {
        return !dayTodoVO.getScariness().equals(changedDayTodoVO.getScariness())
            || !dayTodoVO.getHardness().equals(changedDayTodoVO.getHardness());
    }

    @Override
    public void deleteById(String dayTodoId) {
        dayTodoGateway.deleteById(dayTodoId);
    }

    @Override
    public List<DayTodoVO> findByDayId(String dayId) {
        return dayTodoGateway.findByDayId(dayId);
    }

    @Override
    public DayTodoVO updateIsReady(String dayId, String dayTodoId, boolean isReady) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        dayTodoVO.setDay(dayService.findById(dayId));
        dayTodoVO.setReady(isReady);
        dayTodoVO = dayTodoGateway.save(dayTodoVO);
        return experienceGateway.calculateExperience(dayTodoVO);
    }
}


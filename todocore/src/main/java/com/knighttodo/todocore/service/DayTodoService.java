package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.exception.DayTodoNotFoundException;
import com.knighttodo.todocore.exception.UnchangeableFieldUpdateException;
import com.knighttodo.todocore.service.character.ExperienceServ;
import com.knighttodo.todocore.service.privatedb.mapper.DayTodoMapper;
import com.knighttodo.todocore.service.privatedb.repository.DayTodoRepository;
import com.knighttodo.todocore.service.privatedb.representation.DayTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayTodoService {

    private final DayService dayService;
    private final ExperienceServ experienceService;
    private final DayTodoRepository dayTodoRepository;
    private final DayTodoMapper dayTodoMapper;

    @Transactional
    public DayTodoVO save(UUID dayId, DayTodoVO dayTodoVO) {
        dayTodoVO.setDay(dayService.findById(dayId));
        DayTodo dayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(dayTodo);
    }

    public List<DayTodoVO> findAll() {
        return dayTodoRepository.findAllAlive().stream().map(dayTodoMapper::toTodoVO).collect(Collectors.toList());
    }

    public DayTodoVO findById(UUID dayTodoId) {
        return dayTodoRepository.findByIdAlive(dayTodoId).map(dayTodoMapper::toTodoVO)
                .orElseThrow(() -> {
                    log.error(String.format("Day Todo with such id:%s can't be found", dayTodoId));
                    return new DayTodoNotFoundException(String.format("Day Todo with such id:%s can't be found", dayTodoId));
                });
    }

    @Transactional
    public DayTodoVO updateDayTodo(UUID dayTodoId, DayTodoVO changedDayTodoVO) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        checkUpdatePossibility(dayTodoVO, changedDayTodoVO);

        dayTodoVO.setOrderNumber(changedDayTodoVO.getOrderNumber());
        dayTodoVO.setDayTodoName(changedDayTodoVO.getDayTodoName());
        dayTodoVO.setScariness(changedDayTodoVO.getScariness());
        dayTodoVO.setHardness(changedDayTodoVO.getHardness());
        dayTodoVO.setColor(changedDayTodoVO.getColor());
        DayTodo dayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(dayTodo);
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

    @Transactional
    public void deleteById(UUID dayTodoId) {
        dayTodoRepository.softDeleteById(dayTodoId);
    }

    public List<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoRepository.findByDayIdAlive(dayId).stream().map(dayTodoMapper::toTodoVO)
                .collect(Collectors.toList());
    }

    @Transactional
    public DayTodoVO updateIsReady(UUID dayId, UUID dayTodoId, boolean isReady) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        dayTodoVO.setDay(dayService.findById(dayId));
        dayTodoVO.setReady(isReady);
        dayTodoVO = dayTodoMapper.toTodoVO(dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO)));
        return experienceService.calculateExperience(dayTodoVO);
    }

    @Transactional
    public DayTodoVO orderNumberUpdate(UUID dayTodoId, Integer newDayTodoOrderNumber) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        dayTodoVO.setOrderNumber(newDayTodoOrderNumber);
        DayTodo dayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(dayTodo);
    }
}


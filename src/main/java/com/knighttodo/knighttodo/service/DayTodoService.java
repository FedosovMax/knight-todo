package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.exception.DayTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.service.expirience.ExperienceServiceImpl;
import com.knighttodo.knighttodo.service.privatedb.mapper.DayTodoMapper;
import com.knighttodo.knighttodo.service.privatedb.repositary.DayTodoRepository;
import com.knighttodo.knighttodo.service.privatedb.representation.DayTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class DayTodoService {

    private final DayService dayService;
    private final ExperienceServiceImpl experienceService;
    private final DayTodoRepository dayTodoRepository;
    private final DayTodoMapper dayTodoMapper;


    @Transactional
    public DayTodoVO save(UUID dayId, DayTodoVO dayTodoVO) {
        dayTodoVO.setDay(dayService.findById(dayId));
        DayTodo savedDayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(savedDayTodo);
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

        dayTodoVO.setDayTodoName(changedDayTodoVO.getDayTodoName());
        dayTodoVO.setScariness(changedDayTodoVO.getScariness());
        dayTodoVO.setHardness(changedDayTodoVO.getHardness());
        return save(dayTodoId, dayTodoVO);
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

    public Optional<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoRepository.findByIdAlive(dayId).map(dayTodoMapper::toTodoVO);
    }

    @Transactional
    public DayTodoVO updateIsReady(UUID dayId, UUID dayTodoId, boolean isReady) {
        DayTodoVO dayTodoVO = findById(dayTodoId);
        dayTodoVO.setDay(dayService.findById(dayId));
        dayTodoVO.setReady(isReady);
        dayTodoVO = save(dayTodoId, dayTodoVO);
        return experienceService.calculateExperience(dayTodoVO);
    }
}


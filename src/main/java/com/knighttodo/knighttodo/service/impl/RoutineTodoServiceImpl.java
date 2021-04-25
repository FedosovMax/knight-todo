package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.RoutineTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.gateway.RoutineTodoGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.RoutineTodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoutineTodoServiceImpl implements RoutineTodoService {

    private final RoutineTodoGateway routineTodoGateway;
    private final RoutineService routineService;
    private final ExperienceGateway experienceGateway;

    @Override
    public RoutineTodoVO save(String routineId, RoutineTodoVO routineTodoVO) {
        routineTodoVO.setRoutine(routineService.findById(routineId));
        return routineTodoGateway.save(routineTodoVO);
    }

    @Override
    public List<RoutineTodoVO> findAll() {
        return routineTodoGateway.findAll();
    }

    @Override
    public RoutineTodoVO findById(String routineTodoId) {
        return routineTodoGateway.findById(routineTodoId)
                .orElseThrow(() -> new RoutineTodoNotFoundException(String
                        .format("Routine Todo with such id:%s can't be found", routineTodoId)));
    }

    @Override
    public RoutineTodoVO updateRoutineTodo(String routineTodoId, RoutineTodoVO changedRoutineTodoVO) {
        RoutineTodoVO routineTodoVO = findById(routineTodoId);

        checkUpdatePossibility(routineTodoVO, changedRoutineTodoVO);

        routineTodoVO.setRoutineTodoName(changedRoutineTodoVO.getRoutineTodoName());
        routineTodoVO.setScariness(changedRoutineTodoVO.getScariness());
        routineTodoVO.setHardness(changedRoutineTodoVO.getHardness());
        return routineTodoGateway.save(routineTodoVO);
    }

    private void checkUpdatePossibility(RoutineTodoVO routineTodoVO, RoutineTodoVO changedRoutineTodoVO) {
        if (routineTodoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(routineTodoVO, changedRoutineTodoVO)) {
            throw new UnchangeableFieldUpdateException("Can not update routine todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(RoutineTodoVO dayTodoVO, RoutineTodoVO changedDayTodoVO) {
        return !dayTodoVO.getScariness().equals(changedDayTodoVO.getScariness())
                || !dayTodoVO.getHardness().equals(changedDayTodoVO.getHardness());
    }

    @Override
    public void deleteById(String routineTodoId) {
        routineTodoGateway.deleteById(routineTodoId);
    }

    @Override
    public List<RoutineTodoVO> findByRoutineId(String routineId) {
        return routineTodoGateway.findByRoutineId(routineId);
    }

    @Override
    public RoutineTodoVO updateIsReady(String routineId, String routineTodoId, boolean isReady) {
        RoutineTodoVO routineTodoVO = findById(routineTodoId);
        routineTodoVO.setRoutine(routineService.findById(routineId));
        routineTodoVO.setReady(isReady);
        routineTodoVO = routineTodoGateway.save(routineTodoVO);
        return experienceGateway.calculateExperience(routineTodoVO);
    }
}

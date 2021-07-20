package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.RoutineTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.gateway.RoutineTodoGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.RoutineTodoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoutineTodoServiceImpl implements RoutineTodoService {

    private final RoutineTodoGateway routineTodoGateway;
    private final RoutineService routineService;

    @Override
    public RoutineTodoVO save(UUID routineId, RoutineTodoVO routineTodoVO) {
        routineTodoVO.setRoutineVO(routineService.findById(routineId));
        RoutineTodoVO savedRoutineTodo = routineTodoGateway.save(routineTodoVO);
        savedRoutineTodo.setRoutineVO(routineTodoVO.getRoutineVO());
        return savedRoutineTodo;
    }

    @Override
    public List<RoutineTodoVO> findAll() {
        return routineTodoGateway.findAll();
    }

    @Override
    public RoutineTodoVO findById(UUID routineTodoId) {
        return routineTodoGateway.findById(routineTodoId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Todo with such id:%s can't be found", routineTodoId));
                    return new RoutineTodoNotFoundException(String
                            .format("Routine Todo with such id:%s can't be found", routineTodoId));
                });
    }

    @Override
    public RoutineTodoVO updateRoutineTodo(UUID routineTodoId, RoutineTodoVO changedRoutineTodoVO) {
        RoutineTodoVO routineTodoVO = findById(routineTodoId);

        checkUpdatePossibility(routineTodoVO, changedRoutineTodoVO);

        routineTodoVO.setRoutineTodoName(changedRoutineTodoVO.getRoutineTodoName());
        routineTodoVO.setScariness(changedRoutineTodoVO.getScariness());
        routineTodoVO.setHardness(changedRoutineTodoVO.getHardness());
        return routineTodoGateway.save(routineTodoVO);
    }

    private void checkUpdatePossibility(RoutineTodoVO routineTodoVO, RoutineTodoVO changedRoutineTodoVO) {
        if (routineTodoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(routineTodoVO, changedRoutineTodoVO)) {
            log.error("Can not update routine Todo's field in ready state");
            throw new UnchangeableFieldUpdateException("Can not update routine todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(RoutineTodoVO dayTodoVO, RoutineTodoVO changedDayTodoVO) {
        return !dayTodoVO.getScariness().equals(changedDayTodoVO.getScariness())
                || !dayTodoVO.getHardness().equals(changedDayTodoVO.getHardness());
    }

    @Override
    public void deleteById(UUID routineTodoId) {
        routineTodoGateway.deleteById(routineTodoId);
    }

    @Override
    public List<RoutineTodoVO> findByRoutineId(UUID routineId) {
        return routineTodoGateway.findByRoutineId(routineId);
    }
}

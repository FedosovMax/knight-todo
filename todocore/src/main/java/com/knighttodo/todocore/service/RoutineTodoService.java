package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.exception.RoutineTodoNotFoundException;
import com.knighttodo.todocore.exception.UnchangeableFieldUpdateException;
import com.knighttodo.todocore.gateway.RoutineTodoGateway;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineTodoService {

    private final RoutineTodoGateway routineTodoGateway;
    private final RoutineService routineService;

    @Transactional
    public RoutineTodoVO save(UUID routineId, RoutineTodoVO routineTodoVO) {
        routineTodoVO.setRoutineVO(routineService.findById(routineId));
        RoutineTodoVO savedRoutineTodo = routineTodoGateway.save(routineTodoVO);
        savedRoutineTodo.setRoutineVO(routineTodoVO.getRoutineVO());
        return savedRoutineTodo;
    }

    public List<RoutineTodoVO> findAll() {
        return routineTodoGateway.findAll();
    }

    public RoutineTodoVO findById(UUID routineTodoId) {
        return routineTodoGateway.findById(routineTodoId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Todo with such id:%s can't be found", routineTodoId));
                    return new RoutineTodoNotFoundException(String
                            .format("Routine Todo with such id:%s can't be found", routineTodoId));
                });
    }

    @Transactional
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

    @Transactional
    public void deleteById(UUID routineTodoId) {
        routineTodoGateway.deleteAllRoutineTodoInstancesByRoutineTodoId(routineTodoId);
        routineTodoGateway.deleteById(routineTodoId);
    }

    public List<RoutineTodoVO> findByRoutineId(UUID routineId) {
        return routineTodoGateway.findByRoutineId(routineId);
    }
}

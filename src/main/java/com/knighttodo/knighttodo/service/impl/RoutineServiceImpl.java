package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineGateway;
import com.knighttodo.knighttodo.service.RoutineService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Slf4j
@Service
public class RoutineServiceImpl implements RoutineService {

    private final RoutineGateway routineGateway;

    @Override
    @Transactional
    public RoutineVO save(RoutineVO routineVO) {
        return routineGateway.save(routineVO);
    }

    @Override
    public List<RoutineVO> findAll() {
        return routineGateway.findAll();
    }

    @Override
    public RoutineVO findById(UUID routineId) {
        return routineGateway.findById(routineId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine with such id:%s can't be " + "found", routineId));
                    return new RoutineNotFoundException(
                            String.format("Routine with such id:%s can't be " + "found", routineId));
                });
    }

    @Override
    @Transactional
    public RoutineVO updateRoutine(UUID routineId, RoutineVO changedRoutineVO) {
        RoutineVO routineVO = findById(routineId);
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        return routineGateway.save(routineVO);
    }

    @Override
    @Transactional
    public void deleteById(UUID routineId) {
        routineGateway.deleteAllRoutineInstancesByRoutineId(routineId);
        routineGateway.deleteAllRoutineTodosByRoutineId(routineId);
        routineGateway.deleteById(routineId);
    }

    @Override
    @Transactional
    public List<RoutineTodoInstanceVO> updateRoutineTodoInstances(UUID routineId,
                                                                  List<RoutineTodoInstanceVO> routineTodoInstanceVOs) {
        RoutineVO routineVO = findById(routineId);
        List<RoutineTodoVO> routineTodoVOs = routineVO.getRoutineTodos();
        for (RoutineTodoVO routineTodoVO : routineTodoVOs) {
            routineTodoInstanceVOs.forEach(routineTodoInstanceVO -> {
                if (routineTodoInstanceVO.getRoutineTodo().getId().equals(routineTodoVO.getId())) {
                    routineTodoInstanceVO.setHardness(routineTodoVO.getHardness());
                    routineTodoInstanceVO.setScariness(routineTodoVO.getScariness());
                    routineTodoInstanceVO.setRoutineTodoName(routineTodoVO.getRoutineTodoName());
                }
            });
        }
        return routineTodoInstanceVOs;
    }
}

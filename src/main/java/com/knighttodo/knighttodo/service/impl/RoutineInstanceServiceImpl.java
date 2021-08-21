package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineInstanceNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineInstanceGateway;
import com.knighttodo.knighttodo.service.RoutineInstanceService;
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
public class RoutineInstanceServiceImpl implements RoutineInstanceService {

    private final RoutineInstanceGateway routineInstanceGateway;
    private final RoutineService routineService;

    @Override
    @Transactional
    public RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO, UUID routineId) {
        RoutineVO foundRoutine = routineService.findById(routineId);
        routineInstanceVO.setRoutine(foundRoutine);
        return routineInstanceGateway.save(routineInstanceVO);
    }

    @Override
    public List<RoutineInstanceVO> findAll() {
        return routineInstanceGateway.findAll();
    }

    @Override
    @Transactional
    public RoutineInstanceVO findById(UUID routineInstanceId) {
        RoutineInstanceVO routineInstanceVO = routineInstanceGateway.findById(routineInstanceId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                    return new RoutineInstanceNotFoundException(
                            String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                });
        List<RoutineTodoInstanceVO> routineTodoInstances = routineInstanceVO.getRoutineTodoInstances();
        routineService.updateRoutineTodoInstances(routineInstanceVO.getRoutine().getId(), routineTodoInstances);
        return routineInstanceVO;
    }

    @Override
    @Transactional
    public RoutineInstanceVO update(UUID routineInstanceId, RoutineInstanceVO changedRoutineInstanceVO) {
        RoutineInstanceVO routineInstanceVO = findById(routineInstanceId);
        routineInstanceVO.setName(changedRoutineInstanceVO.getName());
        routineInstanceVO.setHardness(changedRoutineInstanceVO.getHardness());
        routineInstanceVO.setScariness(changedRoutineInstanceVO.getScariness());
        routineInstanceVO.setReady(changedRoutineInstanceVO.isReady());
        return routineInstanceGateway.save(routineInstanceVO);
    }

    @Override
    @Transactional
    public void deleteById(UUID routineInstanceId) {
        routineInstanceGateway.deleteAllRoutineTodoInstancesByRoutineInstanceId(routineInstanceId);
        routineInstanceGateway.deleteById(routineInstanceId);
    }
}

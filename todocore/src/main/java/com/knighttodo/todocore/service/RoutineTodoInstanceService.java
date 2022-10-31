package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.exception.RoutineTodoNotFoundException;
import com.knighttodo.todocore.gateway.RoutineTodoInstanceGateway;
import com.knighttodo.todocore.service.character.ExperienceGateway;
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
public class RoutineTodoInstanceService {

    private final RoutineTodoInstanceGateway routineTodoInstanceGateway;
    private final RoutineInstanceService routineInstanceService;
    private final ExperienceGateway experienceGateway;

    @Transactional
    public RoutineTodoInstanceVO save(UUID routineInstanceId, RoutineTodoInstanceVO routineTodoInstanceVO) {
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findById(routineInstanceId));
        RoutineTodoInstanceVO savedRoutineTodoInstance = routineTodoInstanceGateway.save(routineTodoInstanceVO);
        savedRoutineTodoInstance.setRoutineInstanceVO(routineTodoInstanceVO.getRoutineInstanceVO());
        return savedRoutineTodoInstance;
    }

    public List<RoutineTodoInstanceVO> findAll() {
        return routineTodoInstanceGateway.findAll();
    }

    @Transactional
    public RoutineTodoInstanceVO findById(UUID routineTodoInstanceId) {
        RoutineTodoInstanceVO routineTodoInstanceVO = findRoutineTodoInstanceVO(routineTodoInstanceId);
        return synchronizeWithRoutineTodoAndReturn(routineTodoInstanceVO);
    }

    private RoutineTodoInstanceVO findRoutineTodoInstanceVO(UUID routineTodoInstanceId) {
        return routineTodoInstanceGateway.findById(routineTodoInstanceId)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Todo Instance with such id:%s can't be found", routineTodoInstanceId));
                    return new RoutineTodoNotFoundException(String
                            .format("Routine Todo Instance with such id:%s can't be found", routineTodoInstanceId));
                });
    }

    private RoutineTodoInstanceVO synchronizeWithRoutineTodoAndReturn(RoutineTodoInstanceVO routineTodoInstanceVO) {
        RoutineTodoVO routineTodoVO = routineTodoInstanceVO.getRoutineTodoVO();
        routineTodoInstanceVO.setRoutineTodoInstanceName(routineTodoVO.getRoutineTodoName());
        routineTodoInstanceVO.setHardness(routineTodoVO.getHardness());
        routineTodoInstanceVO.setScariness(routineTodoVO.getScariness());
        routineTodoInstanceGateway.save(routineTodoInstanceVO);
        return routineTodoInstanceVO;
    }

    public List<RoutineTodoInstanceVO> findByRoutineInstanceId(UUID routineId) {
        return routineTodoInstanceGateway.findByRoutineId(routineId);
    }

    @Transactional
    public void deleteById(UUID routineTodoInstanceId) {
        routineTodoInstanceGateway.deleteById(routineTodoInstanceId);
    }

    @Transactional
    public RoutineTodoInstanceVO updateIsReady(UUID routineId, UUID routineTodoId, boolean isReady) {
        RoutineTodoInstanceVO routineTodoInstanceVO = findRoutineTodoInstanceVO(routineTodoId);
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findRoutineInstanceVO(routineId));
        routineTodoInstanceVO.setReady(isReady);
        routineTodoInstanceGateway.save(routineTodoInstanceVO);
        return experienceGateway.calculateExperience(routineTodoInstanceVO);
    }
}

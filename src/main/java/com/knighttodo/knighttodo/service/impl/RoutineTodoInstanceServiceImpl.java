package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.RoutineTodoNotFoundException;
import com.knighttodo.knighttodo.gateway.RoutineTodoInstanceGateway;
import com.knighttodo.knighttodo.gateway.experience.ExperienceGateway;
import com.knighttodo.knighttodo.service.RoutineInstanceService;
import com.knighttodo.knighttodo.service.RoutineTodoInstanceService;
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
public class RoutineTodoInstanceServiceImpl implements RoutineTodoInstanceService {

    private final RoutineTodoInstanceGateway routineTodoInstanceGateway;
    private final RoutineInstanceService routineInstanceService;
    private final ExperienceGateway experienceGateway;

    @Override
    @Transactional
    public RoutineTodoInstanceVO save(UUID routineInstanceId, RoutineTodoInstanceVO routineTodoInstanceVO) {
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findById(routineInstanceId));
        RoutineTodoInstanceVO savedRoutineTodoInstance = routineTodoInstanceGateway.save(routineTodoInstanceVO);
        savedRoutineTodoInstance.setRoutineInstanceVO(routineTodoInstanceVO.getRoutineInstanceVO());
        return savedRoutineTodoInstance;
    }

    @Override
    public List<RoutineTodoInstanceVO> findAll() {
        return routineTodoInstanceGateway.findAll();
    }

    @Override
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

    @Override
    public List<RoutineTodoInstanceVO> findByRoutineInstanceId(UUID routineId) {
        return routineTodoInstanceGateway.findByRoutineId(routineId);
    }

    @Override
    @Transactional
    public void deleteById(UUID routineTodoInstanceId) {
        routineTodoInstanceGateway.deleteById(routineTodoInstanceId);
    }

    @Override
    @Transactional
    public RoutineTodoInstanceVO updateIsReady(UUID routineId, UUID routineTodoId, boolean isReady) {
        RoutineTodoInstanceVO routineTodoInstanceVO = findRoutineTodoInstanceVO(routineTodoId);
        routineTodoInstanceVO.setRoutineInstanceVO(routineInstanceService.findRoutineInstanceVO(routineId));
        routineTodoInstanceVO.setReady(isReady);
        routineTodoInstanceGateway.save(routineTodoInstanceVO);
        return experienceGateway.calculateExperience(routineTodoInstanceVO);
    }
}

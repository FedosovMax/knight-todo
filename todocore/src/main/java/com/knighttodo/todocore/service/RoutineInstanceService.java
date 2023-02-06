package com.knighttodo.todocore.service;

import com.knighttodo.todocore.domain.RoutineInstanceVO;
import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.domain.RoutineVO;
import com.knighttodo.todocore.exception.RoutineInstanceNotFoundException;
import com.knighttodo.todocore.gateway.privatedb.mapper.RoutineInstanceMapper;
import com.knighttodo.todocore.gateway.privatedb.mapper.RoutineTodoInstanceMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.todocore.gateway.privatedb.repository.RoutineTodoInstanceRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.RoutineInstance;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Service
@Transactional(readOnly = true)
public class RoutineInstanceService {

    private final RoutineService routineService;
    private final RoutineTodoInstanceRepository routineTodoInstanceRepository;
    private final RoutineTodoInstanceMapper routineTodoInstanceMapper;
    private final RoutineInstanceRepository routineInstanceRepository;
    private final RoutineInstanceMapper routineInstanceMapper;

    @Transactional
    public RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO, UUID routineId) {
        RoutineVO foundRoutine = routineService.findById(routineId);
        routineInstanceVO.setRoutine(foundRoutine);
        RoutineInstance routineInstance = routineInstanceRepository.save(routineInstanceMapper.toRoutineInstance(routineInstanceVO));
        return routineInstanceMapper.toRoutineInstanceVO(routineInstance);
    }

    public List<RoutineInstanceVO> findAll() {
        return routineInstanceRepository.findAllAlive().stream().map(routineInstanceMapper::toRoutineInstanceVO).collect(Collectors.toList());
    }

    @Transactional
    public RoutineInstanceVO findById(UUID routineInstanceId) {
        RoutineInstanceVO routineInstanceVO = findRoutineInstanceVO(routineInstanceId);
        List<RoutineTodoInstanceVO> routineTodoInstances = routineTodoInstanceRepository.findByRoutineInstanceIdAlive(routineInstanceId).stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO)
                .collect(Collectors.toList());
        routineService.updateRoutineTodoInstances(routineInstanceVO.getRoutine().getId(), routineTodoInstances);
        return routineInstanceVO;
    }

    public RoutineInstanceVO findRoutineInstanceVO(UUID routineInstanceId) {
        return routineInstanceRepository.findByIdAlive(routineInstanceId).map(routineInstanceMapper::toRoutineInstanceVO)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                    return new RoutineInstanceNotFoundException(
                            String.format("Routine Instance with such id:%s can't be " + "found", routineInstanceId));
                });
    }

    @Transactional
    public RoutineInstanceVO update(UUID routineInstanceId, RoutineInstanceVO changedRoutineInstanceVO) {
        RoutineInstanceVO routineInstanceVO = findById(routineInstanceId);
        routineInstanceVO.setName(changedRoutineInstanceVO.getName());
        routineInstanceVO.setHardness(changedRoutineInstanceVO.getHardness());
        routineInstanceVO.setScariness(changedRoutineInstanceVO.getScariness());
        routineInstanceVO.setReady(changedRoutineInstanceVO.isReady());
        RoutineInstance routineInstance = routineInstanceRepository.save(routineInstanceMapper.toRoutineInstance(routineInstanceVO));
        return routineInstanceMapper.toRoutineInstanceVO(routineInstance);
    }

    @Transactional
    public void deleteById(UUID routineInstanceId) {
        routineInstanceRepository.softDeleteAllRoutineTodoInstancesByRoutineInstanceId(routineInstanceId);
        routineInstanceRepository.softDeleteById(routineInstanceId);
        ;
    }
}

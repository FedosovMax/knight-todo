package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.RoutineNotFoundException;
import com.knighttodo.knighttodo.service.privatedb.mapper.RoutineMapper;
import com.knighttodo.knighttodo.service.privatedb.mapper.RoutineTodoMapper;
import com.knighttodo.knighttodo.service.privatedb.repositary.RoutineRepository;
import com.knighttodo.knighttodo.service.privatedb.repositary.RoutineTodoRepository;
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
public class RoutineService {

    private final RoutineRepository routineRepository;
    private final RoutineMapper routineMapper;
    private final RoutineTodoRepository routineTodoRepository;
    private final RoutineTodoMapper routineTodoMapper;

    @Transactional
    public RoutineVO save(RoutineVO routineVO) {
        return routineMapper.toRoutineVO(routineRepository.save(routineMapper.toRoutine(routineVO)));
    }

    public List<RoutineVO> findAll() {
        return routineRepository.findAllAlive().stream().map(routineMapper::toRoutineVO).collect(Collectors.toList());
    }

    public RoutineVO findById(UUID routineId) {
        return routineRepository.findByIdAlive(routineId).map(routineMapper::toRoutineVO)
                .orElseThrow(() -> {
                    log.error(String.format("Routine with such id:%s can't be " + "found", routineId));
                    return new RoutineNotFoundException(
                            String.format("Routine with such id:%s can't be " + "found", routineId));
                });
    }

    @Transactional
    public RoutineVO updateRoutine(UUID routineId, RoutineVO changedRoutineVO) {
        RoutineVO routineVO = findById(routineId);
        routineVO.setName(changedRoutineVO.getName());
        routineVO.setHardness(changedRoutineVO.getHardness());
        routineVO.setScariness(changedRoutineVO.getScariness());
        return save(routineVO);
    }

    @Transactional
    public void deleteById(UUID routineId) {
        routineRepository.softDeleteAllRoutineInstancesByRoutineId(routineId);
        routineRepository.softDeleteAllRoutineTodosByRoutineId(routineId);
        routineRepository.softDeleteById(routineId);
    }

    @Transactional
    public List<RoutineTodoInstanceVO> updateRoutineTodoInstances(UUID routineId,
                                                                  List<RoutineTodoInstanceVO> routineTodoInstanceVOs) {
        RoutineVO routineVO = findById(routineId);
        List<RoutineTodoVO> routineTodoVOs = routineTodoRepository.findByRoutineIdAlive(routineId)
                .stream().map(routineTodoMapper::toRoutineTodoVO).collect(Collectors.toList());
        for (RoutineTodoVO routineTodoVO : routineTodoVOs) {
            routineTodoInstanceVOs.forEach(routineTodoInstanceVO -> {
                if (routineTodoInstanceVO.getRoutineTodoVO().getId().equals(routineTodoVO.getId())) {
                    routineTodoInstanceVO.setHardness(routineTodoVO.getHardness());
                    routineTodoInstanceVO.setScariness(routineTodoVO.getScariness());
                    routineTodoInstanceVO.setRoutineTodoInstanceName(routineTodoVO.getRoutineTodoName());
                }
            });
        }
        return routineTodoInstanceVOs;
    }
}

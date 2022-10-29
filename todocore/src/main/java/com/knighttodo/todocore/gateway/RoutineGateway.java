package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.RoutineVO;
import com.knighttodo.todocore.gateway.privatedb.mapper.RoutineMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.RoutineRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.Routine;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoutineGateway {

    private final RoutineRepository routineRepository;
    private final RoutineMapper routineMapper;

    public RoutineVO save(RoutineVO routineVO) {
        Routine savedRoutine = routineRepository.save(routineMapper.toRoutine(routineVO));
        return routineMapper.toRoutineVO(savedRoutine);
    }

    public List<RoutineVO> findAll() {
        return routineRepository.findAllAlive().stream().map(routineMapper::toRoutineVO).collect(Collectors.toList());
    }

    public Optional<RoutineVO> findById(UUID routineId) {
        return routineRepository.findByIdAlive(routineId).map(routineMapper::toRoutineVO);
    }

    public void deleteById(UUID routineId) {
        routineRepository.softDeleteById(routineId);
    }

    public void deleteAllRoutineInstancesByRoutineId(UUID routineId) {
        routineRepository.softDeleteAllRoutineInstancesByRoutineId(routineId);
    }

    public void deleteAllRoutineTodosByRoutineId(UUID routineId) {
        routineRepository.softDeleteAllRoutineTodosByRoutineId(routineId);
    }
}

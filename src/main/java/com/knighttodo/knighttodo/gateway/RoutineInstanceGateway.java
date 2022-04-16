package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineInstanceMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Component
public class RoutineInstanceGateway {

    private final RoutineInstanceRepository routineInstanceRepository;
    private final RoutineInstanceMapper routineInstanceMapper;

    public RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO) {
        RoutineInstance savedRoutineInstance = routineInstanceRepository.save(routineInstanceMapper.toRoutineInstance(routineInstanceVO));
        return routineInstanceMapper.toRoutineInstanceVO(savedRoutineInstance);
    }

    public List<RoutineInstanceVO> findAll() {
        return routineInstanceRepository.findAllValid().stream().map(routineInstanceMapper::toRoutineInstanceVO).collect(Collectors.toList());
    }

    public Optional<RoutineInstanceVO> findById(UUID routineId) {
        return routineInstanceRepository.findByIdValid(routineId).map(routineInstanceMapper::toRoutineInstanceVO);
    }

    public void deleteById(UUID routineId) {
        routineInstanceRepository.softDeleteById(routineId);
    }

    public void deleteAllRoutineTodoInstancesByRoutineInstanceId(UUID routineInstanceId) {
        routineInstanceRepository.softDeleteAllRoutineTodoInstancesByRoutineInstanceId(routineInstanceId);
    }
}

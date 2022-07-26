package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.gateway.privatedb.mapper.RoutineTodoInstanceMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.RoutineTodoInstanceRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.RoutineTodoInstance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoutineTodoInstanceGateway {

    private final RoutineTodoInstanceRepository routineTodoInstanceRepository;
    private final RoutineTodoInstanceMapper routineTodoInstanceMapper;

    public RoutineTodoInstanceVO save(RoutineTodoInstanceVO routineTodoInstanceVO) {
        RoutineTodoInstance savedRoutineTodoInstance = routineTodoInstanceRepository
                .save(routineTodoInstanceMapper.toRoutineTodoInstance(routineTodoInstanceVO));
        return routineTodoInstanceMapper.toRoutineTodoInstanceVO(savedRoutineTodoInstance);
    }

    public List<RoutineTodoInstanceVO> findAll() {
        return routineTodoInstanceRepository.findAllAlive().stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO).collect(Collectors.toList());
    }

    public Optional<RoutineTodoInstanceVO> findById(UUID routineTodoInstanceId) {
        return routineTodoInstanceRepository.findByIdAlive(routineTodoInstanceId).map(routineTodoInstanceMapper::toRoutineTodoInstanceVO);
    }

    public void deleteById(UUID routineTodoId) {
        routineTodoInstanceRepository.deleteById(routineTodoId);
    }

    public List<RoutineTodoInstanceVO> findByRoutineId(UUID routineInstanceId) {
        return routineTodoInstanceRepository.findByRoutineInstanceIdAlive(routineInstanceId).stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO)
                .collect(Collectors.toList());
    }
}

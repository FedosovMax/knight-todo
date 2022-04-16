package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineTodoInstanceMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineTodoInstanceRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodoInstance;
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
        return routineTodoInstanceRepository.findAllValid().stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO).collect(Collectors.toList());
    }

    public Optional<RoutineTodoInstanceVO> findById(UUID routineTodoInstanceId) {
        return routineTodoInstanceRepository.findByIdValid(routineTodoInstanceId).map(routineTodoInstanceMapper::toRoutineTodoInstanceVO);
    }

    public void deleteById(UUID routineTodoId) {
        routineTodoInstanceRepository.deleteById(routineTodoId);
    }

    public List<RoutineTodoInstanceVO> findByRoutineId(UUID routineInstanceId) {
        return routineTodoInstanceRepository.findByRoutineInstanceIdValid(routineInstanceId).stream()
                .map(routineTodoInstanceMapper::toRoutineTodoInstanceVO)
                .collect(Collectors.toList());
    }
}

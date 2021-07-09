package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.RoutineTodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.RoutineTodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RoutineTodoGatewayImpl implements RoutineTodoGateway {

    private final RoutineTodoRepository routineTodoRepository;
    private final RoutineTodoMapper routineTodoMapper;

    @Override
    public RoutineTodoVO save(RoutineTodoVO routineTodoVO) {
        RoutineTodo savedRoutineTodo = routineTodoRepository.save(routineTodoMapper.toRoutineTodo(routineTodoVO));
        return routineTodoMapper.toRoutineTodoVO(savedRoutineTodo);
    }

    @Override
    public List<RoutineTodoVO> findAll() {
        return routineTodoRepository.findAll().stream().map(routineTodoMapper::toRoutineTodoVO).collect(Collectors.toList());
    }

    @Override
    public Optional<RoutineTodoVO> findById(UUID routineTodoId) {
        return routineTodoRepository.findById(routineTodoId).map(routineTodoMapper::toRoutineTodoVO);
    }

    @Override
    public void deleteById(UUID routineTodoId) {
        routineTodoRepository.deleteById(routineTodoId);
    }

    @Override
    public List<RoutineTodoVO> findByRoutineId(UUID routineId) {
        return routineTodoRepository.findByRoutineId(routineId).stream().map(routineTodoMapper::toRoutineTodoVO)
                .collect(Collectors.toList());
    }
}

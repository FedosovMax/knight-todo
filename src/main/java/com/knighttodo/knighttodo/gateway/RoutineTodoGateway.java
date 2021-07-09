package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineTodoGateway {

    RoutineTodoVO save(RoutineTodoVO routineTodoVO);

    List<RoutineTodoVO> findAll();

    Optional<RoutineTodoVO> findById(UUID routineTodoId);

    void deleteById(UUID routineTodoId);

    List<RoutineTodoVO> findByRoutineId(UUID routineId);
}

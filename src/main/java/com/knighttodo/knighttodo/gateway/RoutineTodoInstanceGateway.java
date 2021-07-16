package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineTodoInstanceGateway {

    RoutineTodoInstanceVO save(RoutineTodoInstanceVO routineTodoInstanceVO);

    List<RoutineTodoInstanceVO> findAll();

    Optional<RoutineTodoInstanceVO> findById(UUID routineTodoInstanceId);

    void deleteById(UUID routineTodoInstanceId);

    List<RoutineTodoInstanceVO> findByRoutineId(UUID routineInstanceId);
}

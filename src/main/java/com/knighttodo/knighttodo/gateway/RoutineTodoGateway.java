package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;

import java.util.List;
import java.util.Optional;

public interface RoutineTodoGateway {

    RoutineTodoVO save(RoutineTodoVO routineTodoVO);

    List<RoutineTodoVO> findAll();

    Optional<RoutineTodoVO> findById(String routineTodoId);

    void deleteById(String routineTodoId);

    List<RoutineTodoVO> findByRoutineId(String routineId);
}

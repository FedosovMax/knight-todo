package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;

import java.util.List;
import java.util.UUID;

public interface RoutineTodoService {

    RoutineTodoVO save(UUID routineId, RoutineTodoVO routineTodoVO);

    List<RoutineTodoVO> findAll();

    RoutineTodoVO findById(UUID routineTodoId);

    RoutineTodoVO updateRoutineTodo(UUID routineTodoId, RoutineTodoVO changedRoutineTodoVO);

    void deleteById(UUID routineTodoId);

    List<RoutineTodoVO> findByRoutineId(UUID routineId);

    RoutineTodoVO updateIsReady(UUID routineId, UUID routineTodoId, boolean isReady);
}

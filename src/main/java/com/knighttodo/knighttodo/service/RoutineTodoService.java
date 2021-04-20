package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;

import java.util.List;

public interface RoutineTodoService {

    RoutineTodoVO save(String routineId, RoutineTodoVO routineTodoVO);

    List<RoutineTodoVO> findAll();

    RoutineTodoVO findById(String routineTodoId);

    RoutineTodoVO updateRoutineTodo(String routineTodoId, RoutineTodoVO changedRoutineTodoVO);

    void deleteById(String routineTodoId);

    List<RoutineTodoVO> findByRoutineId(String routineId);

    RoutineTodoVO updateIsReady(String routineId, String routineTodoId, boolean isReady);
}

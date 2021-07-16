package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;

import java.util.List;
import java.util.UUID;

public interface RoutineTodoInstanceService {

    RoutineTodoInstanceVO save(UUID routineInstanceId, RoutineTodoInstanceVO routineTodoInstanceVO);

    List<RoutineTodoInstanceVO> findAll();

    RoutineTodoInstanceVO findById(UUID routineTodoInstanceId);

    List<RoutineTodoInstanceVO> findByRoutineInstanceId(UUID routineInstanceId);

    RoutineTodoInstanceVO updateIsReady(UUID routineInstanceId, UUID routineTodoInstanceId, boolean isReady);
}

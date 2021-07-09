package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineVO;

import java.util.List;
import java.util.UUID;

public interface RoutineService {

    RoutineVO save(RoutineVO routineVO);

    List<RoutineVO> findAll();

    RoutineVO findById(UUID routineId);

    RoutineVO updateRoutine(UUID routineId, RoutineVO changedRoutineVO);

    void deleteById(UUID routineId);
}

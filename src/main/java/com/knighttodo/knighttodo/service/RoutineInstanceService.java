package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineVO;

import java.util.List;
import java.util.UUID;

public interface RoutineInstanceService {

    RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO);

    List<RoutineInstanceVO> findAll();

    RoutineInstanceVO findById(UUID routineInstanceId);

    RoutineInstanceVO update(UUID routineInstanceId, RoutineInstanceVO changedRoutineInstanceVO);

    void deleteById(UUID routineId);

}

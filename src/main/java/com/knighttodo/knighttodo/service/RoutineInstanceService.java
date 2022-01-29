package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;

import java.util.List;
import java.util.UUID;

public interface RoutineInstanceService {

    RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO, UUID routineId);

    List<RoutineInstanceVO> findAll();

    RoutineInstanceVO findById(UUID routineInstanceId);

    RoutineInstanceVO findRoutineInstanceVO(UUID routineInstanceId);

    RoutineInstanceVO update(UUID routineInstanceId, RoutineInstanceVO changedRoutineInstanceVO);

    void deleteById(UUID routineId);

}

package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineVO;
import java.util.List;

public interface RoutineService {

    RoutineVO save(String blockId, RoutineVO routineVO);

    List<RoutineVO> findAll();

    RoutineVO findById(String routineId);

    RoutineVO updateRoutine(String blockId, String routineId, RoutineVO changedRoutineVO);

    void deleteById(String routineId);

    List<RoutineVO> findAllTemplates();
}

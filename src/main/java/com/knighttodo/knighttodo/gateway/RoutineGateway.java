package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineVO;

import java.util.List;
import java.util.Optional;

public interface RoutineGateway {

    RoutineVO save(RoutineVO routineVO);

    List<RoutineVO> findAll();

    Optional<RoutineVO> findById(String routineId);

    void deleteById(String routineId);

    List<RoutineVO> findAllTemplates();
}

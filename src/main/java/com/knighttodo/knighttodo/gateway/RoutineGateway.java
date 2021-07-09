package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineGateway {

    RoutineVO save(RoutineVO routineVO);

    List<RoutineVO> findAll();

    Optional<RoutineVO> findById(UUID routineId);

    void deleteById(UUID routineId);
}

package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineInstanceGateway {

    RoutineInstanceVO save(RoutineInstanceVO routineInstanceVO);

    List<RoutineInstanceVO> findAll();

    Optional<RoutineInstanceVO> findById(UUID routineInstanceId);

    void deleteById(UUID routineId);
}

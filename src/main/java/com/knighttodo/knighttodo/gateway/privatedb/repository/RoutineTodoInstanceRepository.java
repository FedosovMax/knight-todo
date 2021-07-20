package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodoInstance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface RoutineTodoInstanceRepository extends JpaRepository<RoutineTodoInstance, UUID> {

    List<RoutineTodoInstance> findByRoutineInstanceId(UUID routineInstanceId);
}

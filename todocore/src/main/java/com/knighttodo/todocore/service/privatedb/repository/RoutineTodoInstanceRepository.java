package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.RoutineTodoInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineTodoInstanceRepository extends JpaRepository<RoutineTodoInstance, UUID> {

    @Query("select rti from RoutineTodoInstance  rti where rti.removed=false")
    List<RoutineTodoInstance> findAllAlive();

    @Query("select rti from RoutineTodoInstance rti where rti.id=:routineTodoInstanceId and rti.removed=false ")
    Optional<RoutineTodoInstance> findByIdAlive(@Param("routineTodoInstanceId") UUID routineTodoInstanceId);

    @Query("select rti from RoutineTodoInstance rti where rti.routineInstance.id=:routineInstanceId and " +
            "rti.removed=false")
    List<RoutineTodoInstance> findByRoutineInstanceIdAlive(@Param("routineInstanceId")UUID routineInstanceId);

    @Modifying
    @Query("update RoutineTodoInstance rti set rti.removed=true where rti.id=:routineTodoInstanceId")
    void softDeleteById(@Param("routineTodoInstanceId") UUID routineTodoInstanceId);
}

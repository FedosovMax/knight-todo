package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.RoutineInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineInstanceRepository extends JpaRepository<RoutineInstance, UUID> {

    @Query("select ri from RoutineInstance ri where ri.removed=false")
    List<RoutineInstance> findAllAlive();

    @Query("select ri from RoutineInstance ri where ri.id=:routineInstanceId and ri.removed=false")
    Optional<RoutineInstance> findByIdAlive(@Param("routineInstanceId") UUID routineInstanceId);

    @Query("select ri from RoutineInstance ri where ri.created=:routineInstanceCreated and ri.removed=false")
    Optional<RoutineInstance> findByCreationDateAlive(@Param("routineInstanceCreated")LocalDate creationDate);

    @Modifying
    @Query("update RoutineInstance ri set ri.removed=true where ri.id=:routineInstanceId")
    void softDeleteById(@Param("routineInstanceId") UUID routineInstanceId);

    @Modifying
    @Query("update RoutineTodoInstance rti set rti.removed=true where rti.routineInstance.id=:routineInstanceId and rti.removed=false")
    void softDeleteAllRoutineTodoInstancesByRoutineInstanceId(@Param("routineInstanceId") UUID routineInstanceId);
}

package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoutineRepository extends JpaRepository<Routine, UUID> {

    @Modifying
    @Query("delete from RoutineInstance ri where ri.routine.id=:routineId")
    void deleteAllRoutineInstancesByRoutineId(@Param("routineId") UUID routineId);

    @Modifying
    @Query("delete from RoutineTodo rt where rt.routine.id=:routineId")
    void deleteAllRoutineTodosByRoutineId(@Param("routineId") UUID routineId);

}

package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoutineTodoRepository extends JpaRepository<RoutineTodo, UUID> {

    @Query("select rt from RoutineTodo rt where rt.removed=false")
    List<RoutineTodo> findAllAlive();

    @Query("select rt from RoutineTodo rt where rt.id=:routineTodoId and rt.removed=false")
    Optional<RoutineTodo> findByIdAlive(@Param("routineTodoId") UUID routineTodoId);

    @Query("select rt from RoutineTodo rt where rt.routine.id=:routineId and rt.removed=false")
    List<RoutineTodo> findByRoutineIdAlive(@Param("routineId")UUID routineId);

    @Modifying
    @Query("update RoutineTodo rt set rt.removed=true where rt.id=:routineTodoId")
    void softDeleteById(@Param("routineTodoId") UUID routineTodoId);

    @Modifying
    @Query("update RoutineTodoInstance rti set rti.removed=true where rti.routineTodo.id=:routineTodoId")
    void softDeleteAllRoutineTodoInstancesByRoutineTodoId(@Param("routineTodoId") UUID routineTodoId);
}

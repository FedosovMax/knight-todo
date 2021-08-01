package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface RoutineTodoRepository extends JpaRepository<RoutineTodo, UUID> {

    List<RoutineTodo> findByRoutineId(UUID id);

    @Modifying
    @Query("delete from RoutineTodoInstance rti where rti.routineTodo.id=:routineTodoId")
    void deleteAllRoutineTodoInstancesByRoutineTodoId(@Param("routineTodoId") UUID routineTodoId);
}

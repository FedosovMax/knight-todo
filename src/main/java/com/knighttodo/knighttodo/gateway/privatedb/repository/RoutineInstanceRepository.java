package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface RoutineInstanceRepository extends JpaRepository<RoutineInstance, UUID> {

    @Modifying
    @Query("delete from RoutineTodoInstance rti where rti.routineInstance.id=:routineInstanceId")
    void deleteAllRoutineTodoInstancesByRoutineInstanceId(@Param("routineInstanceId") UUID routineInstanceId);
}

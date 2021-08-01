package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.UUID;

public interface DayRepository extends JpaRepository<Day, UUID> {

    @Modifying
    @Query("delete from DayTodo dt where dt.day.id=:dayId")
    void deleteAllDayTodosByDayId(@Param("dayId") UUID dayId);
}

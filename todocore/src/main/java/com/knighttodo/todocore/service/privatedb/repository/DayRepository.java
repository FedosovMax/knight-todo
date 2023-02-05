package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.Day;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayRepository extends JpaRepository<Day, UUID> {

    @Query("select d from Day d")
    List<Day> findAllAlive();

    @Query("select d from Day d where d.id=:dayId and d.removed = false")
    Optional<Day> findByIdAlive(@Param("dayId") UUID dayId);

    @Modifying
    @Query("update Day d set d.removed = true where d.id=:dayId")
    void softDeleteById(@Param("dayId") UUID dayId);

    @Modifying
    @Query("update DayTodo dt set dt.removed = true where dt.day.id=:dayId")
    void softDeleteAllDayTodosByDayId(@Param("dayId") UUID dayId);

    @Query("select d from Day d where d.date = :date")
    List<Day> findDaysByDate(@Param("date") Date date);
}

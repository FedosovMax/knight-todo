package com.knighttodo.todocore.service.privatedb.repository;

import com.knighttodo.todocore.service.privatedb.representation.DayTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayTodoRepository extends JpaRepository<DayTodo, UUID> {

    @Query("select dt from DayTodo dt where dt.id=:dayTodoId and dt.removed=false")
    Optional<DayTodo> findByIdAlive(@Param("dayTodoId") UUID dayTodoId);

    @Query("select dt from DayTodo dt where dt.removed=false")
    List<DayTodo> findAllAlive();

    @Query("select dt from DayTodo dt where dt.day.id=:dayId and dt.removed=false and dt.day.removed=false")
    List<DayTodo> findByDayIdAlive(@Param("dayId") UUID dayId);

    @Modifying
    @Query("update DayTodo td set td.removed=true where td.id=:dayTodoId")
    void softDeleteById(@Param("dayTodoId") UUID dayTodoId);

    @Query("select dt from DayTodo dt where dt.id=:dayTodoId and dt.orderNumber is not null")
    List<DayTodo> findAllWithOrderNumberAlive(@Param("dayTodoId") UUID dayTodoId);

}

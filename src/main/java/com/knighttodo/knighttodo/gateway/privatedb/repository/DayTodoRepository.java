package com.knighttodo.knighttodo.gateway.privatedb.repository;

import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayTodoRepository extends JpaRepository<DayTodo, UUID> {

    @Modifying
    @Query("select dt from DayTodo dt where dt.id=:dayTodoId and dt.removed=false")
    Optional<DayTodo> findByIdValid(@Param("dayTodoId") UUID dayTodoId);

    @Modifying
    @Query("select dt from DayTodo dt where dt.removed=false")
    List<DayTodo> findAllValid();

    @Modifying
    @Query("select dt from DayTodo dt where dt.day.id=:dayId and dt.removed=false and dt.day.removed=false")
    List<DayTodo> findByDayIdValid(@Param("dayId") UUID dayId);

    @Modifying
    @Query("update DayTodo td set td.removed=true where td.id=:dayTodoId")
    void softDeleteById(@Param("dayTodoId") UUID dayTodoId);
}

package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.DayTodoVO;

import java.util.List;
import java.util.UUID;

public interface DayTodoService {

    DayTodoVO save(UUID dayId, DayTodoVO dayTodoVO);

    List<DayTodoVO> findAll();

    DayTodoVO findById(UUID todoId);

    DayTodoVO updateDayTodo(UUID todoId, DayTodoVO changedDayTodoVO);

    void deleteById(UUID todoId);

    List<DayTodoVO> findByDayId(UUID dayId);

    DayTodoVO updateIsReady(UUID dayId, UUID todoId, boolean isReady);
}

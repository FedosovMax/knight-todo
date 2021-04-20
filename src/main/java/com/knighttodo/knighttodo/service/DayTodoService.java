package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.DayTodoVO;

import java.util.List;

public interface DayTodoService {

    DayTodoVO save(String dayId, DayTodoVO dayTodoVO);

    List<DayTodoVO> findAll();

    DayTodoVO findById(String todoId);

    DayTodoVO updateDayTodo(String todoId, DayTodoVO changedDayTodoVO);

    void deleteById(String todoId);

    List<DayTodoVO> findByDayId(String dayId);

    DayTodoVO updateIsReady(String dayId, String todoId, boolean isReady);
}

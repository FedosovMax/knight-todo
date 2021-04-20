package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayTodoVO;

import java.util.List;
import java.util.Optional;

public interface DayTodoGateway {

    DayTodoVO save(DayTodoVO dayTodoVO);

    List<DayTodoVO> findAll();

    Optional<DayTodoVO> findById(String todoId);

    void deleteById(String todoId);

    List<DayTodoVO> findByDayId(String dayId);
}

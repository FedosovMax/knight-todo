package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayTodoVO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DayTodoGateway {

    DayTodoVO save(DayTodoVO dayTodoVO);

    List<DayTodoVO> findAll();

    Optional<DayTodoVO> findById(UUID todoId);

    void deleteById(UUID todoId);

    List<DayTodoVO> findByDayId(UUID dayId);
}

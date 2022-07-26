package com.knighttodo.todocore.gateway.character;


import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;

public interface ExperienceGateway {

    DayTodoVO calculateExperience(DayTodoVO dayTodoVO);

    RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO);
}

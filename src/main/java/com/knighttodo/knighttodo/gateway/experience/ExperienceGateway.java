package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;

public interface ExperienceGateway {

    DayTodoVO calculateExperience(DayTodoVO dayTodoVO);
    RoutineTodoVO calculateExperience(RoutineTodoVO routineTodoVO);
}

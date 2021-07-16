package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;

public interface ExperienceGateway {

    DayTodoVO calculateExperience(DayTodoVO dayTodoVO);

    RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO);
}

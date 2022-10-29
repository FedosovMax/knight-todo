package com.knighttodo.knighttodo.service.expirience;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;

public interface ExperienceService {

    DayTodoVO calculateExperience(DayTodoVO dayTodoVO);

    RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO);
}

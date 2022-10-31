package com.knighttodo.todocore.service.character;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.service.character.ExperienceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceGatewayImpl implements ExperienceGateway {


    @Override
    public DayTodoVO calculateExperience(DayTodoVO dayTodoVO) {
        //TODO implement calculation
        return dayTodoVO;
    }

    @Override
    public RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO) {
        //TODO implement calculation
        return routineTodoInstanceVO;
    }
}

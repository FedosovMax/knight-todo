package com.knighttodo.todocore.gateway.character;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.domain.RoutineTodoInstanceVO;
import com.knighttodo.todocore.gateway.character.client.ExperienceClient;
import com.knighttodo.todocore.gateway.character.mapper.DayTodoVOMapper;
import com.knighttodo.todocore.gateway.character.mapper.RoutineTodoInstanceVOMapper;
import com.knighttodo.todocore.gateway.character.request.ExperienceRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExperienceGatewayImpl implements ExperienceGateway {

    private final ExperienceClient experienceClient;
    private final DayTodoVOMapper dayTodoVOMapper;
    private final RoutineTodoInstanceVOMapper routineTodoInstanceVOMapper;

    @Override
    public DayTodoVO calculateExperience(DayTodoVO dayTodoVO) {
        ExperienceRequest experienceRequest = dayTodoVOMapper.DayTodoToExperienceRequest(dayTodoVO);
        experienceRequest.setUserId(UUID.randomUUID());
//        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
//        dayTodoVO.setExperience(experienceResponse.getExperience());
        return dayTodoVO;
    }

    @Override
    public RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO) {
        ExperienceRequest experienceRequest = routineTodoInstanceVOMapper.routineTodoInstanceToExperienceRequest(routineTodoInstanceVO);
        experienceRequest.setUserId(UUID.randomUUID());
//        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
//        routineTodoInstanceVO.setExperience(experienceResponse.getExperience());
        return routineTodoInstanceVO;
    }
}

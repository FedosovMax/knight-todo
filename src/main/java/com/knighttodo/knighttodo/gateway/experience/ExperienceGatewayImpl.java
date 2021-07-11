package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.gateway.experience.client.ExperienceClient;
import com.knighttodo.knighttodo.gateway.experience.mapper.DayTodoVOMapper;
import com.knighttodo.knighttodo.gateway.experience.mapper.RoutineTodoVOMapper;
import com.knighttodo.knighttodo.gateway.experience.request.ExperienceRequest;
import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExperienceGatewayImpl implements ExperienceGateway {

    private final ExperienceClient experienceClient;
    private final DayTodoVOMapper dayTodoVOMapper;
    private final RoutineTodoVOMapper routineTodoVOMapper;

    @Override
    public DayTodoVO calculateExperience(DayTodoVO dayTodoVO) {
        ExperienceRequest experienceRequest = dayTodoVOMapper.DayTodoToExperienceRequest(dayTodoVO);
        experienceRequest.setUserId(UUID.randomUUID());
        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
        dayTodoVO.setExperience(experienceResponse.getExperience());
        return dayTodoVO;
    }

    @Override
    public RoutineTodoVO calculateExperience(RoutineTodoVO routineTodoVO) {
        ExperienceRequest experienceRequest = routineTodoVOMapper.routineTodoToExperienceRequest(routineTodoVO);
        experienceRequest.setUserId(UUID.randomUUID());
        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
        routineTodoVO.setExperience(experienceResponse.getExperience());
        return routineTodoVO;
    }
}

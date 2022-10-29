package com.knighttodo.knighttodo.service.expirience;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.service.expirience.client.ExperienceClient;
import com.knighttodo.knighttodo.service.expirience.mapper.DayTodoVOMapper;
import com.knighttodo.knighttodo.service.expirience.mapper.RoutineTodoInstanceVOMapper;
import com.knighttodo.knighttodo.service.expirience.request.ExperienceRequest;
import com.knighttodo.knighttodo.service.expirience.response.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class ExperienceServiceImpl implements ExperienceService {

    private final ExperienceClient experienceClient;
    private final DayTodoVOMapper dayTodoVOMapper;
    private final RoutineTodoInstanceVOMapper routineTodoInstanceVOMapper;

    @Override
    public DayTodoVO calculateExperience(DayTodoVO dayTodoVO) {
        ExperienceRequest experienceRequest = dayTodoVOMapper.DayTodoToExperienceRequest(dayTodoVO);
        experienceRequest.setUserId(UUID.randomUUID());
        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
        dayTodoVO.setExperience(experienceResponse.getExperience());
        return dayTodoVO;
    }

    @Override
    public RoutineTodoInstanceVO calculateExperience(RoutineTodoInstanceVO routineTodoInstanceVO) {
        ExperienceRequest experienceRequest = routineTodoInstanceVOMapper.routineTodoInstanceToExperienceRequest(routineTodoInstanceVO);
        experienceRequest.setUserId(UUID.randomUUID());
        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
        routineTodoInstanceVO.setExperience(experienceResponse.getExperience());
        return routineTodoInstanceVO;
    }
}

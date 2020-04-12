package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.client.ExperienceClient;
import com.knighttodo.knighttodo.gateway.experience.mapper.TodoVOMapper;
import com.knighttodo.knighttodo.gateway.experience.request.ExperienceRequest;
import com.knighttodo.knighttodo.gateway.experience.response.ExperienceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceGatewayImpl implements ExperienceGateway {

    private final ExperienceClient experienceClient;
    private final TodoVOMapper todoVOMapper;

    @Override
    public TodoVO calculateExperience(TodoVO todoVO) {
        ExperienceRequest experienceRequest = todoVOMapper.toTodoRequest(todoVO);
        experienceRequest.setUserId("fakeUserId");
        ExperienceResponse experienceResponse = experienceClient.calculateExperience(experienceRequest);
        todoVO.setExperience(experienceResponse.getExperience());
        return todoVO;
    }
}

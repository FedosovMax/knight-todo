package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.client.ExperienceClient;
import com.knighttodo.knighttodo.gateway.experience.mapper.TodoVOMapper;
import com.knighttodo.knighttodo.gateway.experience.request.TodoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ExperienceGatewayImpl implements ExperienceGateway {

    private final ExperienceClient experienceClient;
    private final TodoVOMapper todoVOMapper;

    @Override
    public void calculateExperience(TodoVO todoVO) {
        TodoRequest todoRequest = todoVOMapper.toTodoRequest(todoVO);
        todoRequest.setUserId("fakeUserId");
        experienceClient.calculateTodo(todoRequest);
    }
}

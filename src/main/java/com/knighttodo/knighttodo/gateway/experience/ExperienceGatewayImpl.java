package com.knighttodo.knighttodo.gateway.experience;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.gateway.experience.client.ExperienceClient;
import com.knighttodo.knighttodo.gateway.experience.mapper.TodoVOMapper;
import com.knighttodo.knighttodo.gateway.experience.request.TodoRequest;
import org.springframework.stereotype.Component;

@Component
public class ExperienceGatewayImpl implements ExperienceGateway {

    ExperienceClient experienceClient;
    TodoVOMapper todoVOMapper;

    @Override
    public void calculateExperience(TodoVO todoVO) {
        TodoRequest todoRequest = new TodoRequest();
        todoRequest.setUserId(1L);
        todoRequest = todoVOMapper.toTodoRequest(todoVO);
        experienceClient.calculateTodo(todoRequest);
    }
}

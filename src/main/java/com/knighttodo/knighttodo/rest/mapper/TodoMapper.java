package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.request.todo.CreateTodoRequest;
import com.knighttodo.knighttodo.rest.request.todo.UpdateTodoRequest;
import com.knighttodo.knighttodo.rest.response.todo.CreateTodoResponse;
import com.knighttodo.knighttodo.rest.response.todo.UpdateTodoResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoMapper {

    TodoVO toTodoVO(CreateTodoRequest request);

    CreateTodoResponse toCreateTodoResponse(TodoVO todoVO);

    TodoVO toTodoVO(UpdateTodoRequest request);

    UpdateTodoResponse toUpdateTodoResponse(TodoVO todoVO);
}

package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.request.todoblock.CreateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.request.todoblock.UpdateTodoBlockRequest;
import com.knighttodo.knighttodo.rest.response.todoblock.CreateTodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.TodoBlockResponse;
import com.knighttodo.knighttodo.rest.response.todoblock.UpdateTodoBlockResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoBlockMapper {

    TodoBlockVO toTodoBlockVO(CreateTodoBlockRequest request);

    CreateTodoBlockResponse toCreateTodoBlockResponse(TodoBlockVO todoBlockVO);

    TodoBlockVO toTodoBlockVO(UpdateTodoBlockRequest request);

    UpdateTodoBlockResponse toUpdateTodoBlockResponse(TodoBlockVO todoBlockVO);

    TodoBlockResponse toTodoBlockResponse(TodoBlockVO todoBlockVO);
}

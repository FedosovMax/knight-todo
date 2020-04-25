package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.rest.request.TodoRequestDto;
import com.knighttodo.knighttodo.rest.response.TodoResponseDto;
import com.knighttodo.knighttodo.rest.response.TodoReadyResponseDto;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface TodoRestMapper {

    @Mapping(target = "blockId", source = "blockVO.id")
    TodoResponseDto toTodoResponseDto(TodoVO todoVO);

    TodoVO toTodoVO(TodoRequestDto requestDto);

    @Named("fromIdToTodoVO")
    @Mapping(target = "id", source = "todoId")
    TodoVO toTodoVO(String todoId);

    @Mapping(target = "blockId", source = "blockVO.id")
    TodoReadyResponseDto toTodoReadyResponseDto(TodoVO todoVO);
}

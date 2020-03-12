package com.knighttodo.knighttodo.rest.mapper;

import com.knighttodo.knighttodo.domain.TodoBlockVO;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.CreateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.request.UpdateTodoBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.CreateTodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.TodoBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.todoblock.response.UpdateTodoBlockResponseDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TodoBlockRestMapper {

    TodoBlockVO toTodoBlockVO(CreateTodoBlockRequestDto requestDto);

    CreateTodoBlockResponseDto toCreateTodoBlockResponseDto(TodoBlockVO todoBlockVO);

    TodoBlockVO toTodoBlockVO(UpdateTodoBlockRequestDto requestDto);

    UpdateTodoBlockResponseDto toUpdateTodoBlockResponseDto(TodoBlockVO todoBlockVO);

    TodoBlockResponseDto toTodoBlockResponseDto(TodoBlockVO todoBlockVO);
}

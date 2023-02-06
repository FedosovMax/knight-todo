package com.knighttodo.todocore.gateway.privatedb.mapper;

import com.knighttodo.todocore.domain.DayVO;
import com.knighttodo.todocore.gateway.privatedb.representation.Day;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DayTodoMapper.class})
public interface DayMapper {

    @Mapping(target = "dayTodos", qualifiedByName = {"DayTodoMapper", "toDayTodoWithoutDay"})
    Day toDay(DayVO dayVO);

    @Mapping(target = "dayTodos", qualifiedByName = {"DayTodoMapper", "toDayTodoVOWithoutDay"})
    DayVO toDayVO(Day day);
}

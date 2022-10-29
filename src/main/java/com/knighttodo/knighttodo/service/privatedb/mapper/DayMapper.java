package com.knighttodo.knighttodo.service.privatedb.mapper;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.service.privatedb.representation.Day;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {DayTodoMapper.class})
public interface DayMapper {

    @Mapping(target = "dayTodos", qualifiedByName = {"DayTodoMapper", "toDayTodoWithoutDay"})
    Day toDay(DayVO dayVO);

    @Mapping(target = "dayTodos", qualifiedByName = {"DayTodoMapper", "toDayTodoVOWithoutDay"})
    DayVO toDayVO(Day day);
}

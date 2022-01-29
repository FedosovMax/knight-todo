package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("RoutineTodoMapper")
@Mapper(componentModel = "spring", uses = {RoutineInstanceMapper.class})
public interface RoutineTodoMapper {

    @Mapping(target = "routine", qualifiedByName = {"toRoutineTodoWithoutRoutine"})
    RoutineTodo toRoutineTodo(RoutineTodoVO routineTodoVO);

    @Mapping(target = "routineVO", qualifiedByName = {"toRoutineTodoVOWithoutRoutine"})
    RoutineTodoVO toRoutineTodoVO(RoutineTodo routineTodo);

    @Named("toRoutineTodos")
    @IterableMapping(qualifiedByName = "toRoutineTodoWithoutRoutine")
    List<RoutineTodo> toRoutineTodos(List<RoutineTodoVO> routineTodoVOS);

    @Named("toRoutineTodoVOs")
    @IterableMapping(qualifiedByName = "toRoutineTodoVOWithoutRoutine")
    List<RoutineTodoVO> toRoutineTodoVOs(List<RoutineTodo> routineTodos);

    @Named("toRoutineTodoWithoutRoutine")
    @Mapping(target = "routine", ignore = true)
    RoutineTodo toRoutineTodoWithoutRoutine(RoutineTodoVO routineTodoVO);

    @Named("toRoutineTodoVOWithoutRoutine")
    @Mapping(target = "routineVO", ignore = true)
    RoutineTodoVO toRoutineTodoVOWithoutRoutine(RoutineTodo routineTodo);

    @Named("toRoutineTodoVOWithoutRoutineAndRoutineTodoInstances")
    @Mapping(target = "routineVO", ignore = true)
    @Mapping(target = "routineTodoInstances", ignore = true)
    RoutineTodoVO toRoutineTodoVOWithoutRoutineAndRoutineTodoInstances(RoutineTodo routineTodo);
}

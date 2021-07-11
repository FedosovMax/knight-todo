package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("RoutineTodoMapper")
@Mapper(componentModel = "spring", uses = {RoutineMapper.class, DayMapper.class})
public interface RoutineTodoMapper {

    @Mapping(target = "routineInstance", qualifiedByName = {"toRoutineTodoWithoutRoutineInstance"})
    RoutineTodo toRoutineTodo(RoutineTodoVO routineTodoVO);

    @Mapping(target = "routineInstanceVO", qualifiedByName = {"toRoutineTodoVOWithoutRoutineInstance"})
    RoutineTodoVO toRoutineTodoVO(RoutineTodo routineTodo);

    @Named("toRoutineTodos")
    @IterableMapping(qualifiedByName = "toRoutineTodoWithoutRoutineInstance")
    List<RoutineTodo> toRoutineTodos(List<RoutineTodoVO> routineTodoVOS);

    @Named("toRoutineTodoVOs")
    @IterableMapping(qualifiedByName = "toRoutineTodoVOWithoutRoutineInstance")
    List<RoutineTodoVO> toRoutineTodoVOs(List<RoutineTodo> routineTodos);

    @Named("toRoutineTodoWithoutRoutineInstance")
    @Mapping(target = "routineInstance", ignore = true)
    RoutineTodo toRoutineTodoWithoutRoutine(RoutineTodoVO routineTodoVO);

    @Named("toRoutineTodoVOWithoutRoutineInstance")
    @Mapping(target = "routineInstanceVO", ignore = true)
    RoutineTodoVO toRoutineTodoVOWithoutRoutine(RoutineTodo routineTodo);
}

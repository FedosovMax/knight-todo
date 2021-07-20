package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodo;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineTodoInstance;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Named("RoutineTodoInstanceMapper")
@Mapper(componentModel = "spring", uses = {RoutineInstanceMapper.class})
public interface RoutineTodoInstanceMapper {

    @Mapping(target = "routineInstance", qualifiedByName = {"toRoutineTodoInstanceWithoutRoutineInstance"})
    RoutineTodoInstance toRoutineTodoInstance(RoutineTodoInstanceVO routineTodoInstanceVO);

    @Mapping(target = "routineInstanceVO", qualifiedByName = {"toRoutineTodoVOWithoutRoutineInstance"})
    RoutineTodoInstanceVO toRoutineTodoInstanceVO(RoutineTodoInstance routineTodoInstance);

    @Named("toRoutineTodos")
    @IterableMapping(qualifiedByName = "toRoutineTodoWithoutRoutineInstance")
    List<RoutineTodo> toRoutineTodos(List<RoutineTodoVO> routineTodoVOS);

    @Named("toRoutineTodoVOs")
    @IterableMapping(qualifiedByName = "toRoutineTodoVOWithoutRoutineInstance")
    List<RoutineTodoVO> toRoutineTodoVOs(List<RoutineTodo> routineTodos);

    @Named("toRoutineTodoInstanceWithoutRoutineInstance")
    @Mapping(target = "routineInstance", ignore = true)
    RoutineTodoInstance toRoutineTodoInstanceWithoutRoutineInstance(RoutineTodoInstanceVO routineTodoInstanceVO);

    @Named("toRoutineTodoVOWithoutRoutineInstance")
    @Mapping(target = "routineInstanceVO", ignore = true)
    RoutineTodoInstanceVO toRoutineTodoInstanceVOWithoutRoutineInstance(RoutineTodoInstance routineTodoInstance);
}

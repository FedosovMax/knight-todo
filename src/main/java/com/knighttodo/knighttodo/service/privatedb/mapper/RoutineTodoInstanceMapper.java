package com.knighttodo.knighttodo.service.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.service.privatedb.representation.RoutineInstance;
import com.knighttodo.knighttodo.service.privatedb.representation.RoutineTodoInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineTodoInstanceMapper")
@Mapper(componentModel = "spring", uses = {RoutineInstanceMapper.class, RoutineTodoMapper.class})
public interface RoutineTodoInstanceMapper {

    @Mapping(target = "routineInstance", source = "routineInstanceVO", qualifiedByName = {"toRoutineInstanceWithoutRoutineTodoInstances"})
    @Mapping(target = "routineTodo", source = "routineTodoVO")
    RoutineTodoInstance toRoutineTodoInstance(RoutineTodoInstanceVO routineTodoInstanceVO);

    @Mapping(target = "routineInstanceVO", source = "routineInstance", qualifiedByName = "toRoutineInstanceVOWithoutRoutineTodoInstances")
    @Mapping(target = "routineTodoVO", source = "routineTodo", qualifiedByName = "toRoutineTodoVOWithoutRoutineAndRoutineTodoInstances")
    RoutineTodoInstanceVO toRoutineTodoInstanceVO(RoutineTodoInstance routineTodoInstance);

    @Named("toRoutineTodoInstanceWithoutRoutineInstance")
    @Mapping(target = "routineInstance", ignore = true)
    RoutineTodoInstance toRoutineTodoInstanceWithoutRoutineInstance(RoutineTodoInstanceVO routineTodoInstanceVO);

    @Named("toRoutineInstanceVOWithoutRoutineTodoInstances")
    @Mapping(target = "routineTodoInstances", ignore = true)
    @Mapping(target = "routine", ignore = true)
    RoutineInstanceVO toRoutineInstanceVOWithoutRoutineTodoInstances(RoutineInstance routineInstance);
}

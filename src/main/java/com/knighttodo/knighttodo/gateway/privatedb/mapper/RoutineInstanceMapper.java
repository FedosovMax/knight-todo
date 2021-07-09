package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Named("RoutineInstanceMapper")
@Mapper(componentModel = "spring", uses = {RoutineTodoMapper.class})
public interface RoutineInstanceMapper {

//    @Mapping(target = "routineTodos", qualifiedByName = {"RoutineTodoMapper", "toRoutineTodos"})
//    @Mapping(target = "routineTodos", qualifiedByName = {"RoutineTodoMapper", "toRoutineTodos"})
    RoutineInstance toRoutineInstance(RoutineInstanceVO routineInstanceVO);

//    @Mapping(target = "routineTodos", source = "routineTodos")
    RoutineInstanceVO toRoutineInstanceVO(RoutineInstance routineInstance);
}

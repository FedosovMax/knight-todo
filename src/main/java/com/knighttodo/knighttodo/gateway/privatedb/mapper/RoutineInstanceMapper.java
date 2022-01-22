package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.RoutineInstance;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Named("RoutineInstanceMapper")
@Mapper(componentModel = "spring")
public interface RoutineInstanceMapper {

    RoutineInstance toRoutineInstance(RoutineInstanceVO routineInstanceVO);

    RoutineInstanceVO toRoutineInstanceVO(RoutineInstance routineInstance);
}

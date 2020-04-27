package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {RoutineMapper.class, TodoMapper.class})
public interface BlockMapper {

    @Mapping(target = "routines", qualifiedByName = {"RoutineMapper", "toRoutineWithoutBlock"})
    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodoWithoutRoutineAndBlock"})
    Block toBlock(BlockVO blockVO);

    @Mapping(target = "routines", qualifiedByName = {"RoutineMapper", "toRoutineVOWithoutBlock"})
    @Mapping(target = "todos", qualifiedByName = {"TodoMapper", "toTodoVOWithoutRoutineAndBlock"})
    BlockVO toBlockVO(Block block);
}

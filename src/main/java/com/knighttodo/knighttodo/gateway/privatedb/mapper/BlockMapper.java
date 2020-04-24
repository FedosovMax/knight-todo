package com.knighttodo.knighttodo.gateway.privatedb.mapper;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BlockMapper {

    Block toBlock(BlockVO blockVO);

    BlockVO toBlockVO(Block block);
}

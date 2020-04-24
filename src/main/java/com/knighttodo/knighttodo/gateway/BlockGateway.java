package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.BlockVO;

import java.util.List;
import java.util.Optional;

public interface BlockGateway {

    BlockVO save(BlockVO blockVO);

    List<BlockVO> findAll();

    Optional<BlockVO> findById(String blockId);

    void deleteById(String blockId);
}

package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.BlockVO;

import java.util.List;

public interface BlockService {

    BlockVO save(BlockVO blockVO);

    List<BlockVO> findAll();

    BlockVO findById(String blockId);

    BlockVO updateBlock(String blockId, BlockVO changedBlockVO);

    void deleteById(String blockId);
}

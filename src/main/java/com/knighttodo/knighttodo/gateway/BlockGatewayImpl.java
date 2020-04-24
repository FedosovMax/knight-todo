package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.BlockMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.BlockRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.Block;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class BlockGatewayImpl implements BlockGateway {

    private final BlockRepository blockRepository;
    private final BlockMapper blockMapper;

    @Override
    public BlockVO save(BlockVO blockVO) {
        Block savedBlock = blockRepository.save(blockMapper.toBlock(blockVO));
        return blockMapper.toBlockVO(savedBlock);
    }

    @Override
    public List<BlockVO> findAll() {
        return blockRepository.findAll().stream().map(blockMapper::toBlockVO).collect(Collectors.toList());
    }

    @Override
    public Optional<BlockVO> findById(String blockId) {
        return blockRepository.findById(blockId).map(blockMapper::toBlockVO);
    }

    @Override
    public void deleteById(String blockId) {
        blockRepository.deleteById(blockId);
    }
}

package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.exception.BlockNotFoundException;
import com.knighttodo.knighttodo.gateway.BlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.BlockMapper;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.BlockService;

import java.util.List;
import java.util.stream.Collectors;

import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {

    private final BlockGateway blockGateway;
    private final BlockMapper blockMapper;
    private RoutineService routineService;

    @Autowired
    public void setRoutineService(RoutineService routineService) {
        this.routineService = routineService;
    }

    @Override
    public BlockVO save(BlockVO blockVO) {
        blockVO = blockGateway.save(blockVO);
        fetchRoutines(blockVO);
        return blockVO;
    }

    @Override
    public List<BlockVO> findAll() {
        List<BlockVO> blockVOS = blockGateway.findAll();
        blockVOS.forEach(this::fetchRoutines);
        return blockVOS;
    }

    @Override
    public BlockVO findById(String blockId) {
        BlockVO blockVO = blockGateway.findById(blockId)
            .orElseThrow(() -> new BlockNotFoundException(
                String.format("Block with such id:%s can't be " + "found", blockId)));
        return fetchRoutines(blockVO);
    }

    @Override
    public BlockVO updateBlock(String blockId, BlockVO changedBlockVO) {
        changedBlockVO.setId(blockId);
        BlockVO blockVO = blockGateway.findById(blockMapper.toBlock(changedBlockVO).getId())
            .orElseThrow(() -> new BlockNotFoundException(
                String.format("Block with such id:%s is not found", blockId)));

        blockVO.setBlockName(changedBlockVO.getBlockName());
        fetchRoutines(blockVO);
        return blockGateway.save(blockVO);
    }

    @Override
    public void deleteById(String blockId) {
        blockGateway.deleteById(blockId);
    }

    private BlockVO fetchRoutines(BlockVO blockVO) {
        List<RoutineVO> routines = routineService.findAllTemplates()
            .stream().map(this::copyRoutine).collect(Collectors.toList());
        blockVO.setRoutines(routines);
        return blockVO;
    }

    private RoutineVO copyRoutine(RoutineVO routineVO) {
        RoutineVO routine = new RoutineVO();
        routine.setTemplateId(routineVO.getTemplateId());
        routine.setHardness(routineVO.getHardness());
        routine.setScariness(routineVO.getScariness());
        routine.setName(routineVO.getName());
        routine.setTodos(routineVO.getTodos());
        routine.setBlock(routineVO.getBlock());
        return routine;
    }
}

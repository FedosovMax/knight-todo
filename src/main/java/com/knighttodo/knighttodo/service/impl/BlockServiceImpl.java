package com.knighttodo.knighttodo.service.impl;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.BlockNotFoundException;
import com.knighttodo.knighttodo.gateway.BlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.BlockMapper;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.BlockService;

import java.util.ArrayList;
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
        fetchRoutines(blockVO);
        mapTodosFromRoutinesToBlock(blockVO);
        return blockGateway.save(blockVO);
    }

    @Override
    public List<BlockVO> findAll() {
        List<BlockVO> blockVOS = blockGateway.findAll();
        blockVOS.forEach(this::fetchRoutines);
        return blockVOS;
    }

    @Override
    public BlockVO findById(String blockId) {
        return blockGateway.findById(blockId)
            .orElseThrow(() -> new BlockNotFoundException(
                String.format("Block with such id:%s can't be " + "found", blockId)));
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

    private void fetchRoutines(BlockVO blockVO) {
        List<RoutineVO> routines = routineService.findAllTemplates()
            .stream().map(this::copyRoutine).collect(Collectors.toList());
        blockVO.setRoutines(routines);
    }

    private RoutineVO copyRoutine(RoutineVO routineVO) {
        RoutineVO routine = new RoutineVO();
        copyTodosToRoutine(routine, routineVO);
        routine.setTemplateId(routineVO.getTemplateId());
        routine.setHardness(routineVO.getHardness());
        routine.setScariness(routineVO.getScariness());
        routine.setName(routineVO.getName());
        routine.setBlock(routineVO.getBlock());
        return routine;
    }

    private void copyTodosToRoutine(RoutineVO routine, RoutineVO routineVO) {
        routine.setTodos(routineVO.getTodos().stream().map(this::copyTodo).collect(Collectors.toList()));
        routine.getTodos().forEach(todoVO -> todoVO.setRoutine(routine));
    }

    private TodoVO copyTodo(TodoVO todoVO) {
        TodoVO todo = new TodoVO();
        todo.setTodoName(todoVO.getTodoName());
        todo.setScariness(todoVO.getScariness());
        todo.setHardness(todoVO.getHardness());
        todo.setReady(false);
        return todo;
    }

    private void mapTodosFromRoutinesToBlock(BlockVO blockVO) {
        List<TodoVO> todoVOs = new ArrayList<>();
        blockVO.getRoutines().forEach(routineVO -> todoVOs.addAll(routineVO.getTodos()));
        todoVOs.forEach(todoVO -> todoVO.setBlock(blockVO));
        blockVO.setTodos(todoVOs);
    }
}

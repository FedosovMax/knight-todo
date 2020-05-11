package com.knighttodo.knighttodo.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.domain.TodoVO;
import com.knighttodo.knighttodo.exception.BlockNotFoundException;
import com.knighttodo.knighttodo.gateway.BlockGateway;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.BlockMapper;
import com.knighttodo.knighttodo.service.BlockService;
import com.knighttodo.knighttodo.service.RoutineService;
import com.knighttodo.knighttodo.service.TodoService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class BlockServiceImpl implements BlockService {

    private final BlockGateway blockGateway;
    private final BlockMapper blockMapper;
    private RoutineService routineService;
    private TodoService todoService;

    @Autowired
    public void setRoutineService(RoutineService routineService) {
        this.routineService = routineService;
    }

    @Autowired
    public void setTodoService(TodoService todoService) {
        this.todoService = todoService;
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
        List<RoutineVO> routineVOS = routineService.findAllTemplates();
        routineVOS.stream().map(this::copyRoutine).collect(Collectors.toList());
        blockVO.setRoutines(routineVOS);
    }

    private RoutineVO copyRoutine(RoutineVO routineVO) {
        RoutineVO updatedRoutineVO = new RoutineVO();
        updatedRoutineVO.setHardness(routineVO.getHardness());
        updatedRoutineVO.setScariness(routineVO.getScariness());
        updatedRoutineVO.setName(routineVO.getName());
        updatedRoutineVO.setBlock(routineVO.getBlock());
        copyTodosToRoutine(updatedRoutineVO, routineVO);

        updatedRoutineVO = routineService.save(routineVO.getBlock().getId(), updatedRoutineVO);
        return updatedRoutineVO;
    }

    private void copyTodosToRoutine(RoutineVO updatedRoutineVO, RoutineVO routineVO) {
        updatedRoutineVO
            .setTodos(routineVO.getTodos().stream().map(todo -> copyTodo(routineVO.getBlock().getId(), todo))
                .collect(Collectors.toList()));
    }

    private TodoVO copyTodo(String blockId, TodoVO todoVO) {
        TodoVO updatedTodo = new TodoVO();
        updatedTodo.setTodoName(todoVO.getTodoName());
        updatedTodo.setScariness(todoVO.getScariness());
        updatedTodo.setHardness(todoVO.getHardness());
        updatedTodo.setReady(false);
        updatedTodo.setRoutine(todoVO.getRoutine());
        updatedTodo = todoService.save(blockId, updatedTodo);
        return updatedTodo;
    }

    private void mapTodosFromRoutinesToBlock(BlockVO blockVO) {
        List<TodoVO> todoVOs = new ArrayList<>();
        blockVO.getRoutines().forEach(routineVO -> todoVOs.addAll(routineVO.getTodos()));
        todoVOs.forEach(todoVO -> todoVO.setBlock(blockVO));
        blockVO.setTodos(todoVOs);
    }
}

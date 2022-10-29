package com.knighttodo.knighttodo.service;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.RoutineTodoNotFoundException;
import com.knighttodo.knighttodo.exception.UnchangeableFieldUpdateException;
import com.knighttodo.knighttodo.service.privatedb.mapper.RoutineTodoMapper;
import com.knighttodo.knighttodo.service.privatedb.repositary.RoutineTodoRepository;
import com.knighttodo.knighttodo.service.privatedb.representation.RoutineTodo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RoutineTodoService {


    private final RoutineService routineService;
    private final RoutineTodoRepository routineTodoRepository;
    private final RoutineTodoMapper routineTodoMapper;

    @Transactional
    public RoutineTodoVO save(UUID routineId, RoutineTodoVO routineTodoVO) {
        routineTodoVO.setRoutineVO(routineService.findById(routineId));
        RoutineTodo savedRoutineTodo = routineTodoRepository.save(routineTodoMapper.toRoutineTodo(routineTodoVO));

        RoutineTodoVO savedRoutineTodoVO = routineTodoMapper.toRoutineTodoVO(savedRoutineTodo);
        savedRoutineTodoVO.setRoutineVO(routineTodoVO.getRoutineVO());
        return savedRoutineTodoVO;
    }

    public List<RoutineTodoVO> findAll() {
        return routineTodoRepository.findAllAlive().stream().map(routineTodoMapper::toRoutineTodoVO).collect(Collectors.toList());
    }

    public RoutineTodoVO findById(UUID routineTodoId) {
        return routineTodoRepository.findByIdAlive(routineTodoId).map(routineTodoMapper::toRoutineTodoVO)
                .orElseThrow(() -> {
                    log.error(String.format("Routine Todo with such id:%s can't be found", routineTodoId));
                    return new RoutineTodoNotFoundException(String
                            .format("Routine Todo with such id:%s can't be found", routineTodoId));
                });
    }

    @Transactional
    public RoutineTodoVO updateRoutineTodo(UUID routineTodoId, RoutineTodoVO changedRoutineTodoVO) {
        RoutineTodoVO routineTodoVO = findById(routineTodoId);
        checkUpdatePossibility(routineTodoVO, changedRoutineTodoVO);
        routineTodoVO.setRoutineTodoName(changedRoutineTodoVO.getRoutineTodoName());
        routineTodoVO.setScariness(changedRoutineTodoVO.getScariness());
        routineTodoVO.setHardness(changedRoutineTodoVO.getHardness());
        return routineTodoMapper.toRoutineTodoVO(routineTodoRepository.save(routineTodoMapper.toRoutineTodo(routineTodoVO)));
    }

    private void checkUpdatePossibility(RoutineTodoVO routineTodoVO, RoutineTodoVO changedRoutineTodoVO) {
        if (routineTodoVO.isReady() && isAtLeastOneUnchangeableFieldUpdated(routineTodoVO, changedRoutineTodoVO)) {
            log.error("Can not update routine Todo's field in ready state");
            throw new UnchangeableFieldUpdateException("Can not update routine todo's field in ready state");
        }
    }

    private boolean isAtLeastOneUnchangeableFieldUpdated(RoutineTodoVO dayTodoVO, RoutineTodoVO changedDayTodoVO) {
        return !dayTodoVO.getScariness().equals(changedDayTodoVO.getScariness())
                || !dayTodoVO.getHardness().equals(changedDayTodoVO.getHardness());
    }

    @Transactional
    public void deleteById(UUID routineTodoId) {
        routineTodoRepository.softDeleteAllRoutineTodoInstancesByRoutineTodoId(routineTodoId);
        routineTodoRepository.softDeleteById(routineTodoId);
    }

    public List<RoutineTodoVO> findByRoutineId(UUID routineId) {
        return routineTodoRepository.findByRoutineIdAlive(routineId).stream().map(routineTodoMapper::toRoutineTodoVO)
                .collect(Collectors.toList());
    }
}

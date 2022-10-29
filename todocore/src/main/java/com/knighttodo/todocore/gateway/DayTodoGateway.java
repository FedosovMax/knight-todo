package com.knighttodo.todocore.gateway;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.gateway.privatedb.mapper.DayTodoMapper;
import com.knighttodo.todocore.gateway.privatedb.repository.DayTodoRepository;
import com.knighttodo.todocore.gateway.privatedb.representation.DayTodo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class DayTodoGateway {

    private final DayTodoRepository dayTodoRepository;
    private final DayTodoMapper dayTodoMapper;

    public DayTodoVO save(DayTodoVO dayTodoVO) {
        DayTodo savedDayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(savedDayTodo);
    }

    public List<DayTodoVO> findAll() {
        return dayTodoRepository.findAllAlive().stream().map(dayTodoMapper::toTodoVO).collect(Collectors.toList());
    }

    public Optional<DayTodoVO> findById(UUID todoId) {
        return dayTodoRepository.findByIdAlive(todoId).map(dayTodoMapper::toTodoVO);
    }

    public void deleteById(UUID todoId) {
        dayTodoRepository.softDeleteById(todoId);
    }

    public List<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoRepository.findByDayIdAlive(dayId).stream().map(dayTodoMapper::toTodoVO)
                .collect(Collectors.toList());
    }
}

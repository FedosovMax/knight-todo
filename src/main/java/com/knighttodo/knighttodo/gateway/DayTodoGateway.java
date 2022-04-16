package com.knighttodo.knighttodo.gateway;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.gateway.privatedb.mapper.DayTodoMapper;
import com.knighttodo.knighttodo.gateway.privatedb.repository.DayTodoRepository;
import com.knighttodo.knighttodo.gateway.privatedb.representation.DayTodo;
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
        return dayTodoRepository.findAllValid().stream().map(dayTodoMapper::toTodoVO).collect(Collectors.toList());
    }

    public Optional<DayTodoVO> findById(UUID todoId) {
        return dayTodoRepository.findByIdValid(todoId).map(dayTodoMapper::toTodoVO);
    }

    public void deleteById(UUID todoId) {
        dayTodoRepository.softDeleteById(todoId);
    }

    public List<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoRepository.findByDayIdValid(dayId).stream().map(dayTodoMapper::toTodoVO)
                .collect(Collectors.toList());
    }
}

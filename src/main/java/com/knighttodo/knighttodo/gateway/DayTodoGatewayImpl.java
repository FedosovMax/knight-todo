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
public class DayTodoGatewayImpl implements DayTodoGateway {

    private final DayTodoRepository dayTodoRepository;
    private final DayTodoMapper dayTodoMapper;

    @Override
    public DayTodoVO save(DayTodoVO dayTodoVO) {
        DayTodo savedDayTodo = dayTodoRepository.save(dayTodoMapper.toTodo(dayTodoVO));
        return dayTodoMapper.toTodoVO(savedDayTodo);
    }

    @Override
    public List<DayTodoVO> findAll() {
        return dayTodoRepository.findAll().stream().map(dayTodoMapper::toTodoVO).collect(Collectors.toList());
    }

    @Override
    public Optional<DayTodoVO> findById(UUID todoId) {
        return dayTodoRepository.findById(todoId).map(dayTodoMapper::toTodoVO);
    }

    @Override
    public void deleteById(UUID todoId) {
        dayTodoRepository.deleteById(todoId);
    }

    @Override
    public List<DayTodoVO> findByDayId(UUID dayId) {
        return dayTodoRepository.findByDayId(dayId).stream().map(dayTodoMapper::toTodoVO)
                .collect(Collectors.toList());
    }
}

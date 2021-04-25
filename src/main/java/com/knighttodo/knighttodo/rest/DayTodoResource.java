package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_DAYS;
import static com.knighttodo.knighttodo.Constants.API_BASE_TODOS;
import static com.knighttodo.knighttodo.Constants.BASE_READY;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.rest.mapper.DayTodoRestMapper;
import com.knighttodo.knighttodo.rest.request.DayTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.DayTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.DayTodoResponseDto;
import com.knighttodo.knighttodo.service.DayTodoService;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_DAYS + "/{dayId}" + API_BASE_TODOS)
public class DayTodoResource {

    private final DayTodoService dayTodoService;
    private final DayTodoRestMapper dayTodoRestMapper;

    @PostMapping
    @ApiOperation(value = "Add new Day Todo")
    public ResponseEntity<DayTodoResponseDto> addDayTodo(@PathVariable String dayId,
                                                         @Valid @RequestBody DayTodoRequestDto requestDto) {
        DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
        DayTodoVO savedDayTodoVO = dayTodoService.save(dayId, dayTodoVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dayTodoRestMapper.toDayTodoResponseDto(savedDayTodoVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Day Todos by the day id")
    public ResponseEntity<List<DayTodoResponseDto>> findDayTodosByDayId(@PathVariable String dayId) {
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(dayTodoService.findByDayId(dayId)
                .stream()
                .map(dayTodoRestMapper::toDayTodoResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{dayTodoId}")
    @ApiOperation(value = "Find the Day Todo by id")
    public ResponseEntity<DayTodoReadyResponseDto> findDayTodoById(@PathVariable String dayTodoId) {
        DayTodoVO dayTodoVO = dayTodoService.findById(dayTodoId);
        return ResponseEntity.status(HttpStatus.FOUND).body(dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO));
    }

    @PutMapping("/{dayTodoId}")
    @ApiOperation(value = "Update the Day Todo by id")
    public ResponseEntity<DayTodoResponseDto> updateDayTodo(@PathVariable String dayTodoId,
                                                         @Valid @RequestBody DayTodoRequestDto requestDto) {
        DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
        DayTodoVO updatedDayTodoVO = dayTodoService.updateDayTodo(dayTodoId, dayTodoVO);
        return ResponseEntity.ok().body(dayTodoRestMapper.toDayTodoResponseDto(updatedDayTodoVO));
    }

    @DeleteMapping("/{dayTodoId}")
    @ApiOperation(value = "Delete the Todo by id")
    public ResponseEntity<Void> deleteTodo(@PathVariable String dayTodoId) {
        dayTodoService.deleteById(dayTodoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{dayTodoId}" + BASE_READY)
    @ApiOperation(value = "Update an isReady field")
    public ResponseEntity<DayTodoReadyResponseDto> updateIsReady(@PathVariable String dayId, @PathVariable String dayTodoId,
                                                                 @RequestParam String ready) {
        boolean isReady = Boolean.parseBoolean(ready);
        DayTodoVO dayTodoVO = dayTodoService.updateIsReady(dayId, dayTodoId, isReady);
        return ResponseEntity.status(HttpStatus.OK).body(dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO));
    }
}

package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.DayTodoVO;
import com.knighttodo.knighttodo.rest.mapper.DayTodoRestMapper;
import com.knighttodo.knighttodo.rest.request.DayTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.DayTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.DayTodoResponseDto;
import com.knighttodo.knighttodo.service.DayTodoService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.*;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_DAYS + "/{dayId}" + API_BASE_TODOS)
@Slf4j
public class DayTodoResource {

    private final DayTodoService dayTodoService;
    private final DayTodoRestMapper dayTodoRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new Day Todo")
    public DayTodoResponseDto addDayTodo(@PathVariable String dayId, @Valid @RequestBody DayTodoRequestDto requestDto) {
        log.debug("Rest request to add dayTodo : {}", requestDto);
        DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
        DayTodoVO savedDayTodoVO = dayTodoService.save(dayId, dayTodoVO);
        return dayTodoRestMapper.toDayTodoResponseDto(savedDayTodoVO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Day Todos by the day id")
    public List<DayTodoResponseDto> findDayTodosByDayId(@PathVariable String dayId) {
        log.debug("request for day to get all todos by day id");
        return dayTodoService.findByDayId(dayId)
                .stream()
                .map(dayTodoRestMapper::toDayTodoResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Day Todo by id")
    public DayTodoReadyResponseDto findDayTodoById(@PathVariable String dayTodoId) {
        log.debug("Rest request to get day todo by id : {}", dayTodoId);
        DayTodoVO dayTodoVO = dayTodoService.findById(dayTodoId);
        return dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO);
    }

    @PutMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Day Todo by id")
    public DayTodoResponseDto updateDayTodo(@PathVariable String dayTodoId,
                                                         @Valid @RequestBody DayTodoRequestDto requestDto) {
        log.debug("Rest request to update day todo : {}", requestDto);
        DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
        DayTodoVO updatedDayTodoVO = dayTodoService.updateDayTodo(dayTodoId, dayTodoVO);
        return dayTodoRestMapper.toDayTodoResponseDto(updatedDayTodoVO);
    }

    @DeleteMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Todo by id")
    public void deleteTodo(@PathVariable String dayTodoId) {
        log.debug("Rest request to delete day todo by id : {}", dayTodoId);
        dayTodoService.deleteById(dayTodoId);
    }

    @PutMapping(value = "/{dayTodoId}" + BASE_READY)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an isReady field")
    public DayTodoReadyResponseDto updateIsReady(@PathVariable String dayId, @PathVariable String dayTodoId,
                                                                 @RequestParam String ready) {
        log.debug("request to update isReady field of day todo with id : {}, to value : {}", dayTodoId, ready);
        boolean isReady = Boolean.parseBoolean(ready);
        DayTodoVO dayTodoVO = dayTodoService.updateIsReady(dayId, dayTodoId, isReady);
        return dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO);
    }
}

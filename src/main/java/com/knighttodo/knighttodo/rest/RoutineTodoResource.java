package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.rest.mapper.RoutineTodoRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoResponseDto;
import com.knighttodo.knighttodo.service.RoutineTodoService;
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
@RequestMapping(API_BASE_ROUTINES + "/{routineId}" + API_BASE_TODOS)
@Slf4j
public class RoutineTodoResource {

    private final RoutineTodoService routineTodoService;
    private final RoutineTodoRestMapper routineTodoRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new Routine Todo")
    public RoutineTodoResponseDto addRoutineTodo(@PathVariable String routineId,
                                                                 @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        log.debug("Rest request to add routineTodo : {}", requestDto);
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO savedRoutineTodoVO = routineTodoService.save(routineId, routineTodoVO);
        return routineTodoRestMapper.toRoutineTodoResponseDto(savedRoutineTodoVO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Routine Todos by the routine id")
    public List<RoutineTodoResponseDto> findRoutineTodosByRoutineId(@PathVariable String routineId) {
        log.debug("request for routine to get all todos by routine id");
        return routineTodoService.findByRoutineId(routineId)
                        .stream()
                        .map(routineTodoRestMapper::toRoutineTodoResponseDto)
                        .collect(Collectors.toList());
    }

    @GetMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Routine Todo by id")
    public RoutineTodoReadyResponseDto findRoutineTodoById(@PathVariable String routineTodoId) {
        log.debug("Rest request to get routine todo by id : {}", routineTodoId);
        RoutineTodoVO routineTodoVO = routineTodoService.findById(routineTodoId);
        return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
    }

    @PutMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Routine Todo by id")
    public RoutineTodoResponseDto updateRoutineTodo(@PathVariable String routineTodoId,
                                                             @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        log.info("Rest request to update routine todo : {}", requestDto);
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO updatedRoutineTodoVO = routineTodoService.updateRoutineTodo(routineTodoId, routineTodoVO);
        return routineTodoRestMapper.toRoutineTodoResponseDto(updatedRoutineTodoVO);
    }

    @DeleteMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Routine Todo by id")
    public void deleteTodo(@PathVariable String routineTodoId) {
        log.info("Rest request to delete routine todo by id : {}", routineTodoId);
        routineTodoService.deleteById(routineTodoId);
    }

    @PutMapping(value = "/{routineTodoId}" + BASE_READY)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an isReady field")
    public RoutineTodoReadyResponseDto updateIsReady(@PathVariable String routineId, @PathVariable String routineTodoId,
                                                                     @RequestParam String ready) {
        log.info("request to update isReady field of routine todo with id : {}, to value : {}", routineId, ready);
        boolean isReady = Boolean.parseBoolean(ready);
        RoutineTodoVO routineTodoVO = routineTodoService.updateIsReady(routineId, routineTodoId, isReady);
        return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
    }
}

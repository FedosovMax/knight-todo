package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.rest.mapper.RoutineTodoRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoResponseDto;
import com.knighttodo.knighttodo.service.RoutineTodoService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
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
public class RoutineTodoResource {

    private final RoutineTodoService routineTodoService;
    private final RoutineTodoRestMapper routineTodoRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new Routine Todo", response = RoutineResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoResponseDto addRoutineTodo(@PathVariable String routineId,
                                                 @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO savedRoutineTodoVO = routineTodoService.save(routineId, routineTodoVO);
        return routineTodoRestMapper.toRoutineTodoResponseDto(savedRoutineTodoVO);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Routine Todos by the routine id", response = RoutineTodoResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todos found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<RoutineTodoResponseDto> findRoutineTodosByRoutineId(@PathVariable String routineId) {
        return routineTodoService.findByRoutineId(routineId)
                .stream()
                .map(routineTodoRestMapper::toRoutineTodoResponseDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Routine Todo by id", response = RoutineTodoReadyResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoReadyResponseDto findRoutineTodoById(@PathVariable String routineTodoId) {
        RoutineTodoVO routineTodoVO = routineTodoService.findById(routineTodoId);
        return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
    }

    @PutMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Routine Todo by id", response = RoutineTodoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoResponseDto updateRoutineTodo(@PathVariable String routineTodoId,
                                                    @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO updatedRoutineTodoVO = routineTodoService.updateRoutineTodo(routineTodoId, routineTodoVO);
        return routineTodoRestMapper.toRoutineTodoResponseDto(updatedRoutineTodoVO);
    }

    @DeleteMapping("/{routineTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Routine Todo by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo removed"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public void deleteTodo(@PathVariable String routineTodoId) {
        routineTodoService.deleteById(routineTodoId);
    }

    @PutMapping(value = "/{routineTodoId}" + BASE_READY)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an isReady field", response = RoutineTodoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo isReady updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoReadyResponseDto updateIsReady(@PathVariable String routineId, @PathVariable String routineTodoId,
                                                     @RequestParam String ready) {
        boolean isReady = Boolean.parseBoolean(ready);
        RoutineTodoVO routineTodoVO = routineTodoService.updateIsReady(routineId, routineTodoId, isReady);
        return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
    }
}

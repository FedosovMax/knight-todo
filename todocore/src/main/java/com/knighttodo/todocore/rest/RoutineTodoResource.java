package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.RoutineTodoVO;
import com.knighttodo.todocore.exception.CreateRoutineTodoException;
import com.knighttodo.todocore.exception.FindAllRoutineTodosException;
import com.knighttodo.todocore.exception.FindRoutineTodoByIdException;
import com.knighttodo.todocore.exception.RoutineNotFoundException;
import com.knighttodo.todocore.exception.RoutineTodoCanNotBeDeletedException;
import com.knighttodo.todocore.exception.RoutineTodoNotFoundException;
import com.knighttodo.todocore.exception.UpdateRoutineTodoException;
import com.knighttodo.todocore.rest.mapper.RoutineTodoRestMapper;
import com.knighttodo.todocore.rest.request.RoutineTodoRequestDto;
import com.knighttodo.todocore.rest.response.RoutineResponseDto;
import com.knighttodo.todocore.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.todocore.rest.response.RoutineTodoResponseDto;
import com.knighttodo.todocore.service.RoutineTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.todocore.Constants.*;

@Api(value = "RoutineTodoResource controller")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_ROUTINES + "/{routineId}" + API_BASE_TODOS)
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
        try {
            RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
            RoutineTodoVO savedRoutineTodoVO = routineTodoService.save(UUID.fromString(routineId), routineTodoVO);
            return routineTodoRestMapper.toRoutineTodoResponseDto(savedRoutineTodoVO);
        } catch (RuntimeException ex) {
            log.error("Routine todo hasn't been created.", ex);
            throw new CreateRoutineTodoException("Routine todo hasn't been created.", ex);
        }
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
        try {
            return routineTodoService.findByRoutineId(UUID.fromString(routineId))
                    .stream()
                    .map(routineTodoRestMapper::toRoutineTodoResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Routine todos can't be found.", ex);
            throw new FindAllRoutineTodosException("Routine todos can't be found.", ex);
        }
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
        try {
            RoutineTodoVO routineTodoVO = routineTodoService.findById(UUID.fromString(routineTodoId));
            return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
        } catch (RuntimeException ex) {
            log.error("Routine todo can't be found.", ex);
            throw new FindRoutineTodoByIdException("Routine todo can't be found.", ex);
        }
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
    public RoutineTodoResponseDto updateRoutineTodo(@PathVariable UUID routineTodoId,
                                                    @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        try {
            RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
            RoutineTodoVO updatedRoutineTodoVO = routineTodoService.updateRoutineTodo(routineTodoId, routineTodoVO);
            return routineTodoRestMapper.toRoutineTodoResponseDto(updatedRoutineTodoVO);
        } catch (RoutineNotFoundException e) {
            log.error("Routine todo can't be found.", e);
            throw new RoutineTodoNotFoundException(e.getMessage());
        } catch (RuntimeException ex) {
            log.error("Routine todo can't be updated.", ex);
            throw new UpdateRoutineTodoException("Routine todo can't be updated.", ex);
        }
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
    public void deleteRoutineTodo(@PathVariable UUID routineTodoId) {
        try {
            routineTodoService.deleteById(routineTodoId);
        } catch (RuntimeException ex) {
            log.error("Routine todo can't be deleted.", ex);
            throw new RoutineTodoCanNotBeDeletedException("Routine todo can't be deleted.", ex);
        }
    }
}

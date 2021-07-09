package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineTodoVO;
import com.knighttodo.knighttodo.exception.*;
import com.knighttodo.knighttodo.rest.mapper.RoutineTodoRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineTodoRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoResponseDto;
import com.knighttodo.knighttodo.service.RoutineTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.*;

@Api(value = "RoutineTodoResource controller")
@Slf4j
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
    public RoutineTodoResponseDto updateRoutineTodo(@PathVariable String routineTodoId,
                                                    @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        try {
            RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
            RoutineTodoVO updatedRoutineTodoVO = routineTodoService.updateRoutineTodo(UUID.fromString(routineTodoId), routineTodoVO);
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
    public void deleteRoutineTodo(@PathVariable String routineTodoId) {
        try {
            routineTodoService.deleteById(UUID.fromString(routineTodoId));
        } catch (RuntimeException ex) {
            log.error("Routine todo can't be deleted.", ex);
            throw new RoutineTodoCanNotBeDeletedException("Routine todo can't be deleted.", ex);
        }
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
        try {
            boolean isReady = Boolean.parseBoolean(ready);
            RoutineTodoVO routineTodoVO = routineTodoService
                    .updateIsReady(UUID.fromString(routineId), UUID.fromString(routineTodoId), isReady);
            return routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO);
        } catch (RuntimeException ex) {
            log.error("Routine todo ready can't be updated.", ex);
            throw new RoutineTodoReadyCanNotBeUpdatedException("Routine todo ready can't be updated.", ex);
        }
    }
}

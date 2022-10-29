package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineTodoInstanceVO;
import com.knighttodo.knighttodo.exception.FindAllRoutineTodoInstancesException;
import com.knighttodo.knighttodo.exception.FindRoutineTodoInstanceByIdException;
import com.knighttodo.knighttodo.exception.RoutineTodoInstanceReadyCanNotBeUpdatedException;
import com.knighttodo.knighttodo.rest.request.mapper.RoutineTodoInstanceRestMapper;
import com.knighttodo.knighttodo.rest.response.RoutineTodoInstanceReadyResponseDto;
import com.knighttodo.knighttodo.rest.response.RoutineTodoInstanceResponseDto;
import com.knighttodo.knighttodo.service.RoutineTodoInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.*;

@Api(value = "RoutineTodoInstanceResource controller")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_ROUTINES_INSTANCES + "/{routineInstanceId}" + API_BASE_ROUTINES_TODO_INSTANCES)
public class RoutineTodoInstanceResource {

    private final RoutineTodoInstanceService routineTodoInstanceService;
    private final RoutineTodoInstanceRestMapper routineTodoInstanceRestMapper;

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Routine Todo Instances by the Routine Instance id", response = RoutineTodoInstanceResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo Instances found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<RoutineTodoInstanceResponseDto> findRoutineTodoInstancesByRoutineInstanceId(@PathVariable UUID routineInstanceId) {
        try {
            return routineTodoInstanceService.findByRoutineInstanceId(routineInstanceId)
                    .stream()
                    .map(routineTodoInstanceRestMapper::toRoutineTodoInstanceResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Routine todo Instances can't be found.", ex);
            throw new FindAllRoutineTodoInstancesException("Routine todo Instances can't be found.", ex);
        }
    }

    @GetMapping("/{routineTodoInstanceId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Routine Todo Instance by id", response = RoutineTodoInstanceReadyResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo Instance found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoInstanceResponseDto findRoutineTodoInstanceById(@PathVariable UUID routineTodoInstanceId) {
        try {
            RoutineTodoInstanceVO routineTodoInstanceVO = routineTodoInstanceService.findById(routineTodoInstanceId);
            return routineTodoInstanceRestMapper.toRoutineTodoInstanceResponseDto(routineTodoInstanceVO);
        } catch (RuntimeException ex) {
            log.error("Routine todo instance can't be found.", ex);
            throw new FindRoutineTodoInstanceByIdException("Routine todo instance can't be found.", ex);
        }
    }

    @PutMapping(value = "/{routineTodoInstanceId}" + BASE_READY)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an isReady field", response = RoutineTodoInstanceResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine Todo Instance isReady updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineTodoInstanceReadyResponseDto updateIsReady(@PathVariable UUID routineInstanceId,
                                                             @PathVariable UUID routineTodoInstanceId,
                                                             @RequestParam String ready) {
        try {
            boolean isReady = Boolean.parseBoolean(ready);
            RoutineTodoInstanceVO routineTodoInstanceVO = routineTodoInstanceService
                    .updateIsReady(routineInstanceId, routineTodoInstanceId, isReady);
            return routineTodoInstanceRestMapper.toRoutineTodoInstanceReadyResponseDto(routineTodoInstanceVO);
        } catch (RuntimeException ex) {
            log.error("Routine todo instance ready can't be updated.", ex);
            throw new RoutineTodoInstanceReadyCanNotBeUpdatedException("Routine todo instance ready can't be updated.", ex);
        }
    }
}

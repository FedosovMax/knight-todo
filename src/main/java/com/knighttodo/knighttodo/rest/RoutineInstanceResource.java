package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineInstanceVO;
import com.knighttodo.knighttodo.exception.*;
import com.knighttodo.knighttodo.rest.mapper.RoutineInstanceRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineInstanceRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineInstanceResponseDto;
import com.knighttodo.knighttodo.service.RoutineInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.*;

@Api(value = "RoutineInstanceResource controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_ROUTINES + "/{routineId}" + API_BASE_ROUTINES_INSTANCES)
public class RoutineInstanceResource {

    private final RoutineInstanceService routineInstanceService;
    private final RoutineInstanceRestMapper routineInstanceRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add the new Routine Instance", response = RoutineInstanceResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineInstanceResponseDto addRoutineInstance(@PathVariable UUID routineId,
                                                         @Valid @RequestBody RoutineInstanceRequestDto requestDto) {
        try {
            RoutineInstanceVO routineInstanceVO = routineInstanceRestMapper.toRoutineInstanceVO(requestDto);
            RoutineInstanceVO savedRoutineVO = routineInstanceService.save(routineInstanceVO, routineId);
            return routineInstanceRestMapper.toRoutineInstanceResponseDto(savedRoutineVO);
        } catch (RuntimeException ex) {
            log.error("Routine instance hasn't been created.", ex);
            throw new CreateRoutineInstanceException("Routine instance hasn't been created.", ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Routine Instances", response = RoutineInstanceResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<RoutineInstanceResponseDto> findAllRoutines() {
        try {
            return routineInstanceService.findAll()
                    .stream()
                    .map(routineInstanceRestMapper::toRoutineInstanceResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Routine Instances can't be found.", ex);
            throw new FindAllRoutineInstancesException("Routine Instances can't be found.", ex);
        }
    }

    @GetMapping("/{routineInstanceId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Routine Instances by id", response = RoutineInstanceResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineInstanceResponseDto findRoutineInstanceById(@PathVariable UUID routineInstanceId) {
        try {
            RoutineInstanceVO routineInstanceVO = routineInstanceService.findById(routineInstanceId);
            return routineInstanceRestMapper.toRoutineInstanceResponseDto(routineInstanceVO);
        } catch (RuntimeException ex) {
            log.error("Routine Instance can't be found.", ex);
            throw new FindRoutineInstanceByIdException("Routine Instance can't be found.", ex);
        }
    }

    @PutMapping("/{routineInstanceId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Routine Instance by id", response = RoutineInstanceResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineInstanceResponseDto updateRoutineInstance(@PathVariable String routineInstanceId,
                                                            @Valid @RequestBody RoutineInstanceRequestDto requestDto) {
        try {
            RoutineInstanceVO routineVO = routineInstanceRestMapper.toRoutineInstanceVO(requestDto);
            RoutineInstanceVO updatedRoutineVO = routineInstanceService.update(UUID.fromString(routineInstanceId), routineVO);
            return routineInstanceRestMapper.toRoutineInstanceResponseDto(updatedRoutineVO);
        } catch (RoutineInstanceNotFoundException e) {
            log.error("Routine Instance can't be found.", e);
            throw new RoutineNotFoundException(e.getMessage());
        } catch (RuntimeException ex) {
            log.error("Routine Instance can't be updated.", ex);
            throw new UpdateRoutineInstanceException("Routine Instance can't be updated.", ex);
        }
    }

    @DeleteMapping("/{routineInstanceId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Routine by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine removed"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public void deleteRoutineInstance(@PathVariable String routineInstanceId) {
        try {
            routineInstanceService.deleteById(UUID.fromString(routineInstanceId));
        } catch (RuntimeException ex) {
            log.error("Routine Instance can't be deleted.", ex);
            throw new RoutineInstanceCanNotBeDeletedException("Routine Instance can't be deleted.", ex);
        }
    }
}

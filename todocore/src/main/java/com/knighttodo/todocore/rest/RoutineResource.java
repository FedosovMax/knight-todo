package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.RoutineVO;
import com.knighttodo.todocore.exception.CreateRoutineException;
import com.knighttodo.todocore.exception.FindAllRoutinesException;
import com.knighttodo.todocore.exception.FindRoutineByIdException;
import com.knighttodo.todocore.exception.RoutineCanNotBeDeletedException;
import com.knighttodo.todocore.exception.RoutineNotFoundException;
import com.knighttodo.todocore.exception.UpdateRoutineException;
import com.knighttodo.todocore.rest.mapper.RoutineRestMapper;
import com.knighttodo.todocore.rest.request.RoutineRequestDto;
import com.knighttodo.todocore.rest.response.RoutineResponseDto;
import com.knighttodo.todocore.service.RoutineService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.todocore.Constants.API_BASE_ROUTINES;
import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;

@Api(value = "RoutineResource controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_ROUTINES)
public class RoutineResource {

    private final RoutineService routineService;
    private final RoutineRestMapper routineRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add the new Routine", response = RoutineResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineResponseDto addRoutine(@Valid @RequestBody RoutineRequestDto requestDto) {
        try {
            RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
            RoutineVO savedRoutineVO = routineService.save(routineVO);
            return routineRestMapper.toRoutineResponseDto(savedRoutineVO);
        } catch (RuntimeException ex) {
            log.error("Routine hasn't been created.", ex);
            throw new CreateRoutineException("Routine hasn't been created.", ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Routines", response = RoutineResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<RoutineResponseDto> findAllRoutines() {
        try {
            return routineService.findAll()
                    .stream()
                    .map(routineRestMapper::toRoutineResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Routines can't be found.", ex);
            throw new FindAllRoutinesException("Routines can't be found.", ex);
        }
    }

    @GetMapping("/{routineId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Routine by id", response = RoutineResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineResponseDto findRoutineById(@PathVariable UUID routineId) {
        try {
            RoutineVO routineVO = routineService.findById(routineId);
            return routineRestMapper.toRoutineResponseDto(routineVO);
        } catch (RuntimeException ex) {
            log.error("Routine can't be found.", ex);
            throw new FindRoutineByIdException("Routine can't be found.", ex);
        }
    }

    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find the routine by creation date", response = RoutineResponseDto.class)
    @ApiParam(value = "Find a routine by creation date", example = "2023-02-20", required = true, readOnly = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineResponseDto findRoutineByCreationDate(
            @RequestParam(name = "date", required = true) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate created
    ) {
        try {
            RoutineVO routineVO = routineService.findByCreationDate(created);
            return routineRestMapper.toRoutineResponseDto(routineVO);
        } catch (RuntimeException ex) {
            log.error("Routine can't be found.", ex);
            throw new FindRoutineByIdException("Routine can't be found.", ex);
        }
    }

    @PutMapping("/{routineId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Routine by id", response = RoutineResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public RoutineResponseDto updateRoutine(@PathVariable UUID routineId,
                                            @Valid @RequestBody RoutineRequestDto requestDto) {
        try {
            RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
            RoutineVO updatedRoutineVO = routineService.updateRoutine(routineId, routineVO);
            return routineRestMapper.toRoutineResponseDto(updatedRoutineVO);
        } catch (RoutineNotFoundException e) {
            log.error("Routine can't be found.", e);
            throw new RoutineNotFoundException(e.getMessage());
        } catch (RuntimeException ex) {
            log.error("Routine can't be updated.", ex);
            throw new UpdateRoutineException("Routine can't be updated.", ex);
        }
    }

    @DeleteMapping("/{routineId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Routine by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Routine removed"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public void deleteRoutine(@PathVariable UUID routineId) {
        try {
            routineService.deleteById(routineId);
        } catch (RuntimeException ex) {
            log.error("Routine can't be deleted.", ex);
            throw new RoutineCanNotBeDeletedException("Routine can't be deleted.", ex);
        }
    }
}

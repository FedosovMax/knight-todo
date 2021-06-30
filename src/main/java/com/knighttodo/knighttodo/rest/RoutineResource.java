package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.mapper.RoutineRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.service.RoutineService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_ROUTINES)
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
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO savedRoutineVO = routineService.save(routineVO);
        return routineRestMapper.toRoutineResponseDto(savedRoutineVO);
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
        return routineService.findAll()
                .stream()
                .map(routineRestMapper::toRoutineResponseDto)
                .collect(Collectors.toList());
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
    public RoutineResponseDto findRoutineById(@PathVariable String routineId) {
        RoutineVO routineVO = routineService.findById(routineId);
        return routineRestMapper.toRoutineResponseDto(routineVO);
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
    public RoutineResponseDto updateRoutine(@PathVariable String routineId,
                                            @Valid @RequestBody RoutineRequestDto requestDto) {
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO updatedRoutineVO = routineService.updateRoutine(routineId, routineVO);
        return routineRestMapper.toRoutineResponseDto(updatedRoutineVO);
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
    public void deleteRoutine(@PathVariable String routineId) {
        routineService.deleteById(routineId);
    }
}

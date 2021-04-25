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
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "Add new Routine Todo")
    public ResponseEntity<RoutineTodoResponseDto> addRoutineTodo(@PathVariable String routineId,
                                                                 @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO savedRoutineTodoVO = routineTodoService.save(routineId, routineTodoVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(routineTodoRestMapper.toRoutineTodoResponseDto(savedRoutineTodoVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Routine Todos by the routine id")
    public ResponseEntity<List<RoutineTodoResponseDto>> findRoutineTodosByRoutineId(@PathVariable String routineId) {
        return ResponseEntity.status(HttpStatus.FOUND)
                .body(routineTodoService.findByRoutineId(routineId)
                        .stream()
                        .map(routineTodoRestMapper::toRoutineTodoResponseDto)
                        .collect(Collectors.toList()));
    }

    @GetMapping("/{routineTodoId}")
    @ApiOperation(value = "Find the Routine Todo by id")
    public ResponseEntity<RoutineTodoReadyResponseDto> findRoutineTodoById(@PathVariable String routineTodoId) {
        RoutineTodoVO routineTodoVO = routineTodoService.findById(routineTodoId);
        return ResponseEntity.status(HttpStatus.FOUND).body(routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO));
    }

    @PutMapping("/{routineTodoId}")
    @ApiOperation(value = "Update the Routine Todo by id")
    public ResponseEntity<RoutineTodoResponseDto> updateRoutineTodo(@PathVariable String routineTodoId,
                                                             @Valid @RequestBody RoutineTodoRequestDto requestDto) {
        RoutineTodoVO routineTodoVO = routineTodoRestMapper.toRoutineTodoVO(requestDto);
        RoutineTodoVO updatedRoutineTodoVO = routineTodoService.updateRoutineTodo(routineTodoId, routineTodoVO);
        return ResponseEntity.ok().body(routineTodoRestMapper.toRoutineTodoResponseDto(updatedRoutineTodoVO));
    }

    @DeleteMapping("/{routineTodoId}")
    @ApiOperation(value = "Delete the Routine Todo by id")
    public ResponseEntity<Void> deleteTodo(@PathVariable String routineTodoId) {
        routineTodoService.deleteById(routineTodoId);
        return ResponseEntity.ok().build();
    }

    @PutMapping(value = "/{routineTodoId}" + BASE_READY)
    @ApiOperation(value = "Update an isReady field")
    public ResponseEntity<RoutineTodoReadyResponseDto> updateIsReady(@PathVariable String routineId, @PathVariable String routineTodoId,
                                                                     @RequestParam String ready) {
        boolean isReady = Boolean.parseBoolean(ready);
        RoutineTodoVO routineTodoVO = routineTodoService.updateIsReady(routineId, routineTodoId, isReady);
        return ResponseEntity.status(HttpStatus.OK).body(routineTodoRestMapper.toRoutineTodoReadyResponseDto(routineTodoVO));
    }
}

package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.mapper.RoutineRestMapper;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.service.RoutineService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @ApiOperation(value = "Add the new Routine")
    public ResponseEntity<RoutineResponseDto> addRoutine(@Valid @RequestBody RoutineRequestDto requestDto) {
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO savedRoutineVO = routineService.save(routineVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(routineRestMapper.toRoutineResponseDto(savedRoutineVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Routines")
    public ResponseEntity<List<RoutineResponseDto>> findAllRoutines() {
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(routineService.findAll()
                .stream()
                .map(routineRestMapper::toRoutineResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{routineId}")
    @ApiOperation(value = "Find the Routine by id")
    public ResponseEntity<RoutineResponseDto> findRoutineById(@PathVariable String routineId) {
        RoutineVO routineVO = routineService.findById(routineId);
        return ResponseEntity.status(HttpStatus.FOUND).body(routineRestMapper.toRoutineResponseDto(routineVO));
    }

    @PutMapping("/{routineId}")
    @ApiOperation(value = "Update the Routine by id")
    public ResponseEntity<RoutineResponseDto> updateRoutine(@PathVariable String routineId,
                                                            @Valid @RequestBody RoutineRequestDto requestDto) {
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO updatedRoutineVO = routineService.updateRoutine(routineId, routineVO);
        return ResponseEntity.ok().body(routineRestMapper.toRoutineResponseDto(updatedRoutineVO));
    }

    @DeleteMapping("/{routineId}")
    @ApiOperation(value = "Delete the Routine by id")
    public ResponseEntity<Void> deleteRoutine(@PathVariable String routineId) {
        routineService.deleteById(routineId);
        return ResponseEntity.ok().build();
    }
}

package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.request.RoutineRequestDto;
import com.knighttodo.knighttodo.rest.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.mapper.RoutineRestMapper;
import com.knighttodo.knighttodo.service.RoutineService;

import io.swagger.annotations.ApiOperation;
import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_BLOCKS + "/{blockId}" + API_BASE_ROUTINES)
@Slf4j
public class RoutineResource {

    private final RoutineService routineService;
    private final RoutineRestMapper routineRestMapper;

    @PostMapping
    @ApiOperation(value = "Add the new Routine")
    public ResponseEntity<RoutineResponseDto> addRoutine(@Valid @RequestBody RoutineRequestDto requestDto,
        @PathVariable String blockId) {
        log.info("Rest request to add routine : {}", requestDto);
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO savedRoutineVO = routineService.save(blockId, routineVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(routineRestMapper.toRoutineResponseDto(savedRoutineVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Routines")
    public ResponseEntity<List<RoutineResponseDto>> findAllRoutines() {
        log.info("Rest request to get all routines");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(routineService.findAll()
                .stream()
                .map(routineRestMapper::toRoutineResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{routineId}")
    @ApiOperation(value = "Find the Routine by id")
    public ResponseEntity<RoutineResponseDto> findRoutineById(@PathVariable String routineId) {
        log.info("Rest request to get routine by id : {}", routineId);
        RoutineVO routineVO = routineService.findById(routineId);

        return ResponseEntity.status(HttpStatus.FOUND).body(routineRestMapper.toRoutineResponseDto(routineVO));
    }

    @PutMapping("/{routineId}")
    @ApiOperation(value = "Update the Routine by id")
    public ResponseEntity<RoutineResponseDto> updateRoutine(@PathVariable String blockId,
        @PathVariable String routineId, @Valid @RequestBody RoutineRequestDto requestDto) {
        log.info("Rest request to update routine : {}", requestDto);
        RoutineVO routineVO = routineRestMapper.toRoutineVO(requestDto);
        RoutineVO updatedRoutineVO = routineService.updateRoutine(blockId, routineId, routineVO);

        return ResponseEntity.ok().body(routineRestMapper.toRoutineResponseDto(updatedRoutineVO));
    }

    @DeleteMapping("/{routineId}")
    @ApiOperation(value = "Delete the Routine by id")
    public ResponseEntity<Void> deleteRoutine(@PathVariable String routineId) {
        log.info("Rest request to delete routine by id : {}", routineId);
        routineService.deleteById(routineId);
        return ResponseEntity.ok().build();
    }
}

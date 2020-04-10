package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;
import static com.knighttodo.knighttodo.Constants.API_BASE_ROUTINES;

import com.knighttodo.knighttodo.domain.RoutineVO;
import com.knighttodo.knighttodo.rest.dto.routine.request.CreateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.request.UpdateRoutineRequestDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.CreateRoutineResponseDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.RoutineResponseDto;
import com.knighttodo.knighttodo.rest.dto.routine.response.UpdateRoutineResponseDto;
import com.knighttodo.knighttodo.rest.mapper.RoutineRestMapper;
import com.knighttodo.knighttodo.service.RoutineService;

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

    @GetMapping
    public ResponseEntity<List<RoutineResponseDto>> getAllRoutines() {
        log.info("Rest request to get all routines");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(routineService.findAll()
                .stream()
                .map(routineRestMapper::toRoutineResponseDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CreateRoutineResponseDto> addRoutine(
        @Valid @RequestBody CreateRoutineRequestDto createRequestDto) {
        log.info("Rest request to add routine : {}", createRequestDto);
        RoutineVO routineVO = routineRestMapper.toRoutineVO(createRequestDto);
        RoutineVO savedRoutineVO = routineService.save(routineVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(routineRestMapper.toCreateRoutineResponseDto(savedRoutineVO));
    }

    @GetMapping("/{routineId}")
    public ResponseEntity<RoutineResponseDto> getRoutineById(@PathVariable String routineId) {
        log.info("Rest request to get routine by id : {}", routineId);
        RoutineVO routineVO = routineService.findById(routineId);

        return ResponseEntity.status(HttpStatus.FOUND).body(routineRestMapper.toRoutineResponseDto(routineVO));
    }

    @PutMapping
    public ResponseEntity<UpdateRoutineResponseDto> updateRoutine(
        @Valid @RequestBody UpdateRoutineRequestDto updateRequestDto) {
        log.info("Rest request to update routine : {}", updateRequestDto);
        RoutineVO routineVO = routineRestMapper.toRoutineVO(updateRequestDto);
        RoutineVO updatedRoutineVO = routineService.updateRoutine(routineVO);

        return ResponseEntity.ok().body(routineRestMapper.toUpdateRoutineResponseDto(updatedRoutineVO));
    }

    @DeleteMapping("/{routineId}")
    public ResponseEntity<Void> deleteRoutine(@PathVariable String routineId) {
        log.info("Rest request to delete routine by id : {}", routineId);
        routineService.deleteById(routineId);
        return ResponseEntity.ok().build();
    }
}

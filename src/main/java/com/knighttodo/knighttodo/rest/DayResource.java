package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_DAYS;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;
import com.knighttodo.knighttodo.rest.response.DayResponseDto;
import com.knighttodo.knighttodo.rest.mapper.DayRestMapper;
import com.knighttodo.knighttodo.service.DayService;

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
@RequestMapping(API_BASE_DAYS)
@Slf4j
public class DayResource {

    private final DayService dayService;
    private final DayRestMapper dayRestMapper;

    @PostMapping
    @ApiOperation(value = "Add the new Day")
    public ResponseEntity<DayResponseDto> addDay(@Valid @RequestBody DayRequestDto requestDto) {
        log.debug("Rest request to add day : {}", requestDto);
        DayVO dayVO = dayRestMapper.toDayVO(requestDto);
        DayVO savedDayVO = dayService.save(dayVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(dayRestMapper.toDayResponseDto(savedDayVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Days")
    public ResponseEntity<List<DayResponseDto>> findAllDays() {
        log.debug("Rest request to get all days");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(dayService.findAll()
                .stream()
                .map(dayRestMapper::toDayResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{dayId}")
    @ApiOperation(value = "find the Day by id")
    public ResponseEntity<DayResponseDto> findDayById(@PathVariable String dayId) {
        log.debug("Rest request to get day by id : {}", dayId);
        DayVO dayVO = dayService.findById(dayId);

        return ResponseEntity.status(HttpStatus.FOUND).body(dayRestMapper.toDayResponseDto(dayVO));
    }

    @PutMapping("/{dayId}")
    @ApiOperation(value = "Update the Day by id")
    public ResponseEntity<DayResponseDto> updateDay(@PathVariable String dayId,
                                                      @Valid @RequestBody DayRequestDto requestDto) {
        log.debug("Rest request to update day : {}", requestDto);
        DayVO dayVO = dayRestMapper.toDayVO(requestDto);
        DayVO updatedDayVO = dayService.updateDay(dayId, dayVO);

        return ResponseEntity.ok().body(dayRestMapper.toDayResponseDto(updatedDayVO));
    }

    @DeleteMapping("/{dayId}")
    @ApiOperation(value = "Delete the Day by id")
    public ResponseEntity<Void> deleteDay(@PathVariable String dayId) {
        log.info("Rest request to delete day by id : {}", dayId);
        dayService.deleteById(dayId);
        return ResponseEntity.ok().build();
    }
}

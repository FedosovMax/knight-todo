package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.exception.*;
import com.knighttodo.knighttodo.rest.mapper.DayRestMapper;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;
import com.knighttodo.knighttodo.rest.response.DayResponseDto;
import com.knighttodo.knighttodo.service.DayService;
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

import static com.knighttodo.knighttodo.Constants.API_BASE_DAYS;

@Api(value = "DayResource controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_DAYS)
public class DayResource {

    private final DayService dayService;
    private final DayRestMapper dayRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add the new Day", response = DayResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayResponseDto addDay(@Valid @RequestBody DayRequestDto requestDto) {
        try {
            DayVO dayVO = dayRestMapper.toDayVO(requestDto);
            DayVO savedDayVO = dayService.save(dayVO);
            return dayRestMapper.toDayResponseDto(savedDayVO);
        } catch (RuntimeException ex) {
            log.error("Day hasn't been created.", ex);
            throw new CreateDayException("Day hasn't been created.", ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Days", response = DayResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Days found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<DayResponseDto> findAllDays() {
        try {
            return dayService.findAll()
                    .stream()
                    .map(dayRestMapper::toDayResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Days can't be found.", ex);
            throw new FindAllDaysException("Days can't be found.", ex);
        }
    }

    @GetMapping("/{dayId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "find the Day by id", response = DayResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayResponseDto findDayById(@PathVariable String dayId) {
        try {
            DayVO dayVO = dayService.findById(UUID.fromString(dayId));
            return dayRestMapper.toDayResponseDto(dayVO);
        } catch (RuntimeException ex) {
            log.error("Day can't be found.", ex);
            throw new FindDayByIdException("Day can't be found.", ex);
        }
    }

    @PutMapping("/{dayId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Day by id", response = DayResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayResponseDto updateDay(@PathVariable String dayId, @Valid @RequestBody DayRequestDto requestDto) {
        try {
            DayVO dayVO = dayRestMapper.toDayVO(requestDto);
            DayVO updatedDayVO = dayService.updateDay(UUID.fromString(dayId), dayVO);
            return dayRestMapper.toDayResponseDto(updatedDayVO);
        } catch (DayNotFoundException e) {
            log.error("Day can't be found.", e);
            throw new DayNotFoundException(e.getMessage());
        } catch (RuntimeException ex) {
            log.error("Day can't be updated.", ex);
            throw new UpdateDayException("Day can't be updated.", ex);
        }
    }

    @DeleteMapping("/{dayId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Day by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day removed"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public void deleteDay(@PathVariable String dayId) {
        try {
            dayService.deleteById(UUID.fromString(dayId));
        } catch (RuntimeException ex) {
            log.error("Day can't be deleted.", ex);
            throw new DayCanNotBeDeletedException("Day can't be deleted.", ex);
        }
    }
}

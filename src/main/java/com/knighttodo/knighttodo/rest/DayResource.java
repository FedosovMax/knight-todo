package com.knighttodo.knighttodo.rest;

import com.knighttodo.knighttodo.domain.DayVO;
import com.knighttodo.knighttodo.rest.mapper.DayRestMapper;
import com.knighttodo.knighttodo.rest.request.DayRequestDto;
import com.knighttodo.knighttodo.rest.response.DayResponseDto;
import com.knighttodo.knighttodo.service.DayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.knighttodo.knighttodo.Constants.API_BASE_DAYS;

@Api(value = "DayResource controller")
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
        DayVO dayVO = dayRestMapper.toDayVO(requestDto);
        DayVO savedDayVO = dayService.save(dayVO);
        return dayRestMapper.toDayResponseDto(savedDayVO);
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
        return dayService.findAll()
                .stream()
                .map(dayRestMapper::toDayResponseDto)
                .collect(Collectors.toList());
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
        DayVO dayVO = dayService.findById(dayId);
        return dayRestMapper.toDayResponseDto(dayVO);
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
        DayVO dayVO = dayRestMapper.toDayVO(requestDto);
        DayVO updatedDayVO = dayService.updateDay(dayId, dayVO);
        return dayRestMapper.toDayResponseDto(updatedDayVO);
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
        dayService.deleteById(dayId);
    }
}

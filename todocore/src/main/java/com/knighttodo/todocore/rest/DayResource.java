package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.DayVO;
import com.knighttodo.todocore.exception.CreateDayException;
import com.knighttodo.todocore.exception.DayCanNotBeDeletedException;
import com.knighttodo.todocore.exception.DayNotFoundException;
import com.knighttodo.todocore.exception.FindAllDaysException;
import com.knighttodo.todocore.exception.FindDayByDateException;
import com.knighttodo.todocore.exception.UpdateDayException;
import com.knighttodo.todocore.rest.mapper.DayRestMapper;
import com.knighttodo.todocore.rest.request.DayRequestDto;
import com.knighttodo.todocore.rest.response.DayResponseDto;
import com.knighttodo.todocore.service.DayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.todocore.Constants.API_BASE_DAYS;
import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;

@Api(value = "DayResource controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_DAYS)
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
    public DayResponseDto findDayById(@PathVariable UUID dayId) {
        try {
            DayVO dayVO = dayService.findById(dayId);
            return dayRestMapper.toDayResponseDto(dayVO);
        } catch (DayNotFoundException e) {
            log.error("Day can't be found.", e);
            throw new DayNotFoundException(e.getMessage());
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
    public DayResponseDto updateDay(@PathVariable UUID dayId, @Valid @RequestBody DayRequestDto requestDto) {
        try {
            DayVO dayVO = dayRestMapper.toDayVO(requestDto);
            DayVO updatedDayVO = dayService.updateDay(dayId, dayVO);
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
    public void deleteDay(@PathVariable UUID dayId) {
        try {
            dayService.deleteById(dayId);
        } catch (RuntimeException ex) {
            log.error("Day can't be deleted.", ex);
            throw new DayCanNotBeDeletedException("Day can't be deleted.", ex);
        }
    }

    @GetMapping("/date")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Find a day by Date", response = DayResponseDto.class)
    @ApiParam(value = "Find a day by date", example = "2023-02-06", required = true, readOnly = true)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })

    public DayResponseDto findDayByDate(@RequestParam(name = "date")  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        try {
            return dayRestMapper.toDayResponseDto(dayService.findDayByDate(date));
        } catch (RuntimeException ex) {
            log.error("Day can't be found by date : " + date.toString(), ex);
            throw new FindDayByDateException("Day can't be found.", ex);
        }
    }
}

package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.DayTodoVO;
import com.knighttodo.todocore.exception.CreateDayTodoException;
import com.knighttodo.todocore.exception.DayTodoCanNotBeDeletedException;
import com.knighttodo.todocore.exception.DayTodoNotFoundException;
import com.knighttodo.todocore.exception.DayTodoReadyCanNotBeUpdatedException;
import com.knighttodo.todocore.exception.FindAllDayTodosException;
import com.knighttodo.todocore.exception.FindDayTodoByIdException;
import com.knighttodo.todocore.exception.UpdateDayTodoException;
import com.knighttodo.todocore.exception.UpdateOrderNumberException;
import com.knighttodo.todocore.rest.mapper.DayTodoRestMapper;
import com.knighttodo.todocore.rest.request.DayTodoRequestDto;
import com.knighttodo.todocore.rest.response.DayTodoReadyResponseDto;
import com.knighttodo.todocore.rest.response.DayTodoResponseDto;
import com.knighttodo.todocore.service.DayTodoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
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
import org.springframework.web.bind.annotation.PatchMapping;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.knighttodo.todocore.Constants.*;

@Api(value = "DayTodoResource controller")
@Slf4j
@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_DAYS + "/{dayId}" + API_BASE_TODOS)
public class DayTodoResource {

    private final DayTodoService dayTodoService;
    private final DayTodoRestMapper dayTodoRestMapper;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add new Day Todo", response = DayTodoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayTodoResponseDto addDayTodo(@PathVariable UUID dayId, @Valid @RequestBody DayTodoRequestDto requestDto) {
        try {
            DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
            DayTodoVO savedDayTodoVO = dayTodoService.save(dayId, dayTodoVO);
            return dayTodoRestMapper.toDayTodoResponseDto(savedDayTodoVO);
        } catch (RuntimeException ex) {
            log.error("Day todo hasn't been created.", ex);
            throw new CreateDayTodoException("Day todo hasn't been created.", ex);
        }
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find all Day Todos by the day id", response = DayTodoResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day Todos found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<DayTodoResponseDto> findDayTodosByDayId(@PathVariable UUID dayId) {
        try {
            return dayTodoService.findByDayId(dayId)
                    .stream()
                    .map(dayTodoRestMapper::toDayTodoResponseDto)
                    .collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Day todos can't be found.", ex);
            throw new FindAllDayTodosException("Day todos can't be found.", ex);
        }
    }

    @GetMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.FOUND)
    @ApiOperation(value = "Find the Day Todo by id", response = DayTodoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day Todo found"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayTodoReadyResponseDto findDayTodoById(@PathVariable UUID dayTodoId) {
        try {
            DayTodoVO dayTodoVO = dayTodoService.findById(dayTodoId);
            return dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO);
        } catch (RuntimeException ex) {
            log.error("Day todo can't be found.", ex);
            throw new FindDayTodoByIdException("Day todo can't be found.", ex);
        }
    }

    @PutMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the Day Todo by id", response = DayTodoResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day Todo updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayTodoResponseDto updateDayTodo(@PathVariable UUID dayTodoId,
                                            @Valid @RequestBody DayTodoRequestDto requestDto) {
        try {
            DayTodoVO dayTodoVO = dayTodoRestMapper.toDayTodoVO(requestDto);
            DayTodoVO updatedDayTodoVO = dayTodoService.updateDayTodo(dayTodoId, dayTodoVO);
            return dayTodoRestMapper.toDayTodoResponseDto(updatedDayTodoVO);
        } catch (DayTodoNotFoundException e) {
            log.error("Day todo can't be found.", e);
            throw new DayTodoNotFoundException(e.getMessage());
        } catch (RuntimeException ex) {
            log.error("Day todo can't be updated.", ex);
            throw new UpdateDayTodoException("Day todo can't be updated.", ex);
        }
    }

    @DeleteMapping("/{dayTodoId}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete the Todo by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day Todo removed"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public void deleteTodo(@PathVariable String dayTodoId) {
        try {
            dayTodoService.deleteById(UUID.fromString(dayTodoId));
        } catch (RuntimeException ex) {
            log.error("Day todo can't be deleted.", ex);
            throw new DayTodoCanNotBeDeletedException("Day todo can't be deleted.", ex);
        }
    }

    @PutMapping(value = "/{dayTodoId}" + BASE_READY)
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update an isReady field", response = DayTodoReadyResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Day Todo isReady updated"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 404, message = "Resource not found"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public DayTodoReadyResponseDto updateIsReady(@PathVariable UUID dayId, @PathVariable UUID dayTodoId,
                                                 @RequestParam String ready) {
        try {
            boolean isReady = Boolean.parseBoolean(ready);
            DayTodoVO dayTodoVO = dayTodoService.updateIsReady(dayId, dayTodoId, isReady);
            return dayTodoRestMapper.toDayTodoReadyResponseDto(dayTodoVO);
        } catch (RuntimeException ex) {
            log.error("Day todo ready can't be updated.", ex);
            throw new DayTodoReadyCanNotBeUpdatedException("Day todo ready can't be updated.", ex);
        }
    }

    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Change orderNumber id existed Todo", response = DayTodoResponseDto.class, responseContainer = "List")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "Invalid operation"),
            @ApiResponse(code = 403, message = "Operation forbidden"),
            @ApiResponse(code = 500, message = "Unexpected error")
    })
    public List<DayTodoResponseDto> changeDayTodoOrderNumber(@PathVariable Map<UUID,Integer> orderNumbersMap) {
        try {
            orderNumbersMap.keySet().stream().map(dayTodoService::findById);
            List<DayTodoVO> dayTodoVOS = orderNumbersMap.entrySet().stream()
                    .map(entry ->dayTodoService.orderNumberUpdate(entry.getKey(),entry.getValue()))
                    .collect(Collectors.toList());
            return dayTodoVOS.stream().map(dayTodoRestMapper::toDayTodoResponseDto).collect(Collectors.toList());
        } catch (RuntimeException ex) {
            log.error("Order number does not updated.", ex);
            throw new UpdateOrderNumberException("Order number does not updated.", ex);
        }
    }
}

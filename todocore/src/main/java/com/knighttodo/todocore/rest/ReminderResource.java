package com.knighttodo.todocore.rest;

import com.knighttodo.todocore.domain.ReminderVO;
import com.knighttodo.todocore.rest.mapper.ReminderRestMapper;
import com.knighttodo.todocore.rest.request.CreateReminderRequestDto;
import com.knighttodo.todocore.rest.request.UpdateReminderRequestDto;
import com.knighttodo.todocore.rest.response.ReminderResponseDto;
import com.knighttodo.todocore.service.ReminderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.UUID;

import static com.knighttodo.todocore.Constants.API_BASE_REMINDER;
import static com.knighttodo.todocore.Constants.API_BASE_URL_V1;

@Api(value = "ReminderResource controller")
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(API_BASE_URL_V1 + API_BASE_REMINDER)
public class ReminderResource {

    private final ReminderRestMapper reminderRestMapper;
    private final ReminderService reminderService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiOperation(value = "Add the new Reminder", response = ReminderResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 409, message = "Invalid reminder date")
    })
    public ReminderResponseDto addReminder(@Valid @RequestBody CreateReminderRequestDto requestDto) {
        ReminderVO reminderVO = reminderRestMapper.toReminderVO(requestDto);
        ReminderVO savedReminderVO = reminderService.save(reminderVO);
        return reminderRestMapper.toReminderResponseDto(savedReminderVO);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Update the new Reminder", response = ReminderResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Reminder successfully updated"),
            @ApiResponse(code = 409, message = "Invalid reminder date")
    })
    public ReminderResponseDto updateReminder(@Valid @RequestBody UpdateReminderRequestDto requestDto,
                                           @PathVariable UUID id) {
        ReminderVO reminderVO = reminderRestMapper.toReminderVO(requestDto);
        ReminderVO updatedReminderVO = reminderService.update(id, reminderVO);
        return reminderRestMapper.toReminderResponseDto(updatedReminderVO);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Get Reminder by id", response = ReminderResponseDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Reminder not found")
    })
    public ReminderResponseDto getReminderById(@PathVariable UUID id) {
        ReminderVO reminderVO = reminderService.findById(id);
        return reminderRestMapper.toReminderResponseDto(reminderVO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiOperation(value = "Delete Reminder by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Reminder not found")
    })
    public void deleteReminderById(@PathVariable UUID id) {
        reminderService.deleteById(id);
    }

}

package com.knighttodo.todocore.character.rest;

import com.knighttodo.todocore.character.domain.ExperienceVO;
import com.knighttodo.todocore.character.rest.mappers.ExperienceRestMapper;
import com.knighttodo.todocore.character.rest.request.ExperienceRequestDto;
import com.knighttodo.todocore.character.rest.response.ExperienceResponseDto;
import com.knighttodo.todocore.character.service.ExperienceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import static com.knighttodo.todocore.Constants.BASE_EXPERIENCE_URL;

@RestController
@RequiredArgsConstructor
@RequestMapping(BASE_EXPERIENCE_URL)
@Slf4j
public class ExperienceResource {

    private final ExperienceService experienceService;
    private final ExperienceRestMapper experienceRestMapper;

    @PostMapping
    public ResponseEntity<ExperienceResponseDto> calculateTodo(@Valid @RequestBody ExperienceRequestDto experienceRequestDto) {
        ExperienceVO experienceVO = experienceRestMapper.toExperienceVO(experienceRequestDto);
        experienceVO = experienceService.calculateExperience(experienceVO);
        ExperienceResponseDto experienceResponseDto = experienceRestMapper.toExperienceResponseDto(experienceVO);
        return ResponseEntity.ok().body(experienceResponseDto);
    }
}

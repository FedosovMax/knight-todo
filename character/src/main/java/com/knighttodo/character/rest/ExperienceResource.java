package com.knighttodo.character.rest;

import static com.knighttodo.character.Constants.BASE_EXPERIENCE_URL;

import com.knighttodo.character.domain.ExperienceVO;
import com.knighttodo.character.rest.mappers.ExperienceRestMapper;
import com.knighttodo.character.rest.request.ExperienceRequestDto;
import com.knighttodo.character.rest.response.ExperienceResponseDto;
import com.knighttodo.character.service.ExperienceService;
import javax.validation.Valid;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

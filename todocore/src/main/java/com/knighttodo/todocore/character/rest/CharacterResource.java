package com.knighttodo.todocore.character.rest;

import com.knighttodo.todocore.character.domain.CharacterVO;
import com.knighttodo.todocore.character.rest.mappers.CharacterRestMapper;
import com.knighttodo.todocore.character.rest.request.CharacterRequestDto;
import com.knighttodo.todocore.character.rest.response.CharacterResponseDto;
import com.knighttodo.todocore.character.service.CharacterService;
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

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.knighttodo.todocore.Constants.BASE_CHARACTER;

@RequiredArgsConstructor
@RestController
@RequestMapping(BASE_CHARACTER)
@Slf4j
public class CharacterResource {

    private final CharacterService characterService;
    private final CharacterRestMapper characterRestMapper;

    @GetMapping
    public ResponseEntity<List<CharacterResponseDto>> getAllCharacters() {
        log.info("Rest request to get all characters");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(characterService.findAll().stream().map(characterRestMapper::toCharacterResponseDto)
                .collect(Collectors.toList()));
    }

    @PostMapping
    public ResponseEntity<CharacterResponseDto> addCharacter(
        @Valid @RequestBody CharacterRequestDto createRequestDto) {
        log.info("Rest request to add character : {}", createRequestDto);
        CharacterVO characterVO = characterRestMapper.toCharacterVO(createRequestDto);
        CharacterVO savedCharacterVO = characterService.save(characterVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(characterRestMapper.toCharacterResponseDto(savedCharacterVO));
    }

    @GetMapping("/{characterId}")
    public ResponseEntity<CharacterResponseDto> getCharacterById(@PathVariable String characterId) {
        log.info("Rest request to get character by id : {}", characterId);
        CharacterVO characterVO = characterService.findById(characterId);

        return ResponseEntity.status(HttpStatus.FOUND).body(characterRestMapper.toCharacterResponseDto(characterVO));
    }

    @PutMapping("/{characterId}")
    public ResponseEntity<CharacterResponseDto> updateCharacter(@PathVariable String characterId,
        @Valid @RequestBody CharacterRequestDto updateRequestDto) {
        log.info("Rest request to update character : {}", updateRequestDto);
        CharacterVO characterVO = characterRestMapper.toCharacterVO(updateRequestDto);
        CharacterVO updatedCharacterVO = characterService.updateCharacter(characterVO, characterId);

        return ResponseEntity.ok().body(characterRestMapper.toCharacterResponseDto(updatedCharacterVO));
    }

    @DeleteMapping("/{characterId}")
    public ResponseEntity<Void> deleteCharacter(@PathVariable String characterId) {
        log.info("Rest request to delete character by id : {}", characterId);
        characterService.deleteById(characterId);
        return ResponseEntity.ok().build();
    }
}

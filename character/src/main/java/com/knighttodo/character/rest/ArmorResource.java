package com.knighttodo.character.rest;

import static com.knighttodo.character.Constants.API_BASE_ARMORS;
import static com.knighttodo.character.Constants.API_BASE_ITEMS;

import com.knighttodo.character.domain.ArmorVO;
import com.knighttodo.character.rest.mappers.ArmorRestMapper;
import com.knighttodo.character.rest.request.ArmorRequestDto;
import com.knighttodo.character.rest.response.ArmorResponseDto;
import com.knighttodo.character.service.ArmorService;

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
@RequestMapping(API_BASE_ITEMS + API_BASE_ARMORS)
@Slf4j
public class ArmorResource {

    private final ArmorService armorService;
    private final ArmorRestMapper armorRestMapper;

    @PostMapping
    public ResponseEntity<ArmorResponseDto> addArmor(@Valid @RequestBody ArmorRequestDto requestDto) {
        log.info("Request to add armor : {}", requestDto);
        ArmorVO armorVO = armorRestMapper.toArmorVO(requestDto);
        ArmorVO savedArmorVO = armorService.save(armorVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(armorRestMapper.toArmorResponseDto(savedArmorVO));
    }

    @GetMapping
    public ResponseEntity<List<ArmorResponseDto>> findAllArmors() {
        log.info("Request to find all armors");
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(armorService.findAll().stream()
                .map(armorRestMapper::toArmorResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{armorId}")
    public ResponseEntity<ArmorResponseDto> findArmorById(@PathVariable String armorId) {
        log.info("Request to find armor by id : {}", armorId);
        ArmorVO armorVO = armorService.findById(armorId);
        return ResponseEntity.status(HttpStatus.FOUND).body(armorRestMapper.toArmorResponseDto(armorVO));
    }

    @PutMapping("/{armorId}")
    public ResponseEntity<ArmorResponseDto> updateArmor(@PathVariable String armorId,
        @Valid @RequestBody ArmorRequestDto requestDto) {
        log.info("Request to update armor : {}", requestDto);
        ArmorVO armorVO = armorRestMapper.toArmorVO(requestDto);
        ArmorVO updatedArmorVO = armorService.updateArmor(armorId, armorVO);
        return ResponseEntity.ok().body(armorRestMapper.toArmorResponseDto(updatedArmorVO));
    }

    @DeleteMapping("/{armorId}")
    public ResponseEntity<Void> deleteArmor(@PathVariable String armorId) {
        log.info("Request to delete armor by id : {}", armorId);
        armorService.deleteById(armorId);
        return ResponseEntity.ok().build();
    }
}

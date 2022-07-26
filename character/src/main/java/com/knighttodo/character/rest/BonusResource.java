package com.knighttodo.character.rest;

import static com.knighttodo.character.Constants.API_BASE_BONUSES;

import com.knighttodo.character.domain.BonusVO;
import com.knighttodo.character.rest.mappers.BonusRestMapper;
import com.knighttodo.character.rest.request.BonusRequestDto;
import com.knighttodo.character.rest.response.BonusResponseDto;
import com.knighttodo.character.service.BonusService;

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
@RequestMapping(API_BASE_BONUSES)
@Slf4j
public class BonusResource {

    private final BonusService bonusService;
    private final BonusRestMapper bonusRestMapper;

    @PostMapping
    public ResponseEntity<BonusResponseDto> addBonus(@Valid @RequestBody BonusRequestDto requestDto) {
        log.info("Request to add bonus : {}", requestDto);
        BonusVO bonusVO = bonusRestMapper.toBonusVO(requestDto);
        BonusVO savedBonusVO = bonusService.save(bonusVO);
        return ResponseEntity.status(HttpStatus.CREATED).body(bonusRestMapper.toBonusResponseDto(savedBonusVO));
    }

    @GetMapping
    public ResponseEntity<List<BonusResponseDto>> getAllBonuses() {
        log.info("Request to get all bonuses");
        return ResponseEntity.status(HttpStatus.FOUND)
            .body(bonusService.findAll()
                .stream()
                .map(bonusRestMapper::toBonusResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{bonusId}")
    public ResponseEntity<BonusResponseDto> getBonusById(@PathVariable String bonusId) {
        log.info("Request to get bonus by id : {}", bonusId);
        BonusVO bonusVO = bonusService.findById(bonusId);
        return ResponseEntity.status(HttpStatus.FOUND).body(bonusRestMapper.toBonusResponseDto(bonusVO));
    }

    @PutMapping("/{bonusId}")
    public ResponseEntity<BonusResponseDto> updateBonus(@PathVariable String bonusId,
        @Valid @RequestBody BonusRequestDto requestDto) {
        log.info("Request to update bonus : {}", requestDto);
        BonusVO bonusVO = bonusRestMapper.toBonusVO(requestDto);
        BonusVO updatedBonusVO = bonusService.updateBonus(bonusId, bonusVO);
        return ResponseEntity.ok().body(bonusRestMapper.toBonusResponseDto(updatedBonusVO));
    }

    @DeleteMapping("/{bonusId}")
    public ResponseEntity<Void> deleteBonus(@PathVariable String bonusId) {
        log.info("Request to delete bonus by id : {}", bonusId);
        bonusService.deleteById(bonusId);
        return ResponseEntity.ok().build();
    }
}

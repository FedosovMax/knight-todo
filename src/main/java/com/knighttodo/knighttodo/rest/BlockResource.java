package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.rest.dto.block.request.CreateBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.block.request.UpdateBlockRequestDto;
import com.knighttodo.knighttodo.rest.dto.block.response.CreateBlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.block.response.BlockResponseDto;
import com.knighttodo.knighttodo.rest.dto.block.response.UpdateBlockResponseDto;
import com.knighttodo.knighttodo.rest.mapper.BlockRestMapper;
import com.knighttodo.knighttodo.service.BlockService;

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
@RequestMapping(API_BASE_BLOCKS)
@Slf4j
public class BlockResource {

    private final BlockService blockService;
    private final BlockRestMapper blockRestMapper;

    @PostMapping
    public ResponseEntity<CreateBlockResponseDto> addBlock(
        @Valid @RequestBody CreateBlockRequestDto createRequestDto) {
        log.info("Rest request to add block : {}", createRequestDto);
        BlockVO blockVO = blockRestMapper.toBlockVO(createRequestDto);
        BlockVO savedBlockVO = blockService.save(blockVO);

        return ResponseEntity.status(HttpStatus.CREATED)
            .body(blockRestMapper.toCreateBlockResponseDto(savedBlockVO));
    }

    @GetMapping
    public ResponseEntity<List<BlockResponseDto>> getAllBlocks() {
        log.info("Rest request to get all blocks");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(blockService.findAll()
                .stream()
                .map(blockRestMapper::toBlockResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{blockId}")
    public ResponseEntity<BlockResponseDto> getBlockById(@PathVariable String blockId) {
        log.info("Rest request to get block by id : {}", blockId);
        BlockVO blockVO = blockService.findById(blockId);

        return ResponseEntity.status(HttpStatus.FOUND).body(blockRestMapper.toBlockResponseDto(blockVO));
    }

    @PutMapping("/{blockId}")
    public ResponseEntity<UpdateBlockResponseDto> updateBlock(@PathVariable String blockId,
        @Valid @RequestBody UpdateBlockRequestDto updateRequestDto) {
        log.info("Rest request to update block : {}", updateRequestDto);
        BlockVO blockVO = blockRestMapper.toBlockVO(updateRequestDto);
        BlockVO updatedBlockVO = blockService.updateBlock(blockId, blockVO);

        return ResponseEntity.ok().body(blockRestMapper.toUpdateBlockResponseDto(updatedBlockVO));
    }

    @DeleteMapping("/{blockId}")
    public ResponseEntity<Void> deleteBlock(@PathVariable String blockId) {
        log.info("Rest request to delete block by id : {}", blockId);
        blockService.deleteById(blockId);
        return ResponseEntity.ok().build();
    }
}

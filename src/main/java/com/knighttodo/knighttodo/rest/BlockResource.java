package com.knighttodo.knighttodo.rest;

import static com.knighttodo.knighttodo.Constants.API_BASE_BLOCKS;

import com.knighttodo.knighttodo.domain.BlockVO;
import com.knighttodo.knighttodo.rest.request.BlockRequestDto;
import com.knighttodo.knighttodo.rest.response.BlockResponseDto;
import com.knighttodo.knighttodo.rest.mapper.BlockRestMapper;
import com.knighttodo.knighttodo.service.BlockService;

import io.swagger.annotations.ApiOperation;
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
    @ApiOperation(value = "Add the new Block")
    public ResponseEntity<BlockResponseDto> addBlock(@Valid @RequestBody BlockRequestDto requestDto) {
        log.info("Rest request to add block : {}", requestDto);
        BlockVO blockVO = blockRestMapper.toBlockVO(requestDto);
        BlockVO savedBlockVO = blockService.save(blockVO);

        return ResponseEntity.status(HttpStatus.CREATED).body(blockRestMapper.toBlockResponseDto(savedBlockVO));
    }

    @GetMapping
    @ApiOperation(value = "Find all Blocks")
    public ResponseEntity<List<BlockResponseDto>> findAllBlocks() {
        log.info("Rest request to get all blocks");

        return ResponseEntity.status(HttpStatus.FOUND)
            .body(blockService.findAll()
                .stream()
                .map(blockRestMapper::toBlockResponseDto)
                .collect(Collectors.toList()));
    }

    @GetMapping("/{blockId}")
    @ApiOperation(value = "find the Block by id")
    public ResponseEntity<BlockResponseDto> findBlockById(@PathVariable String blockId) {
        log.info("Rest request to get block by id : {}", blockId);
        BlockVO blockVO = blockService.findById(blockId);

        return ResponseEntity.status(HttpStatus.FOUND).body(blockRestMapper.toBlockResponseDto(blockVO));
    }

    @PutMapping("/{blockId}")
    @ApiOperation(value = "Update the Block by id")
    public ResponseEntity<BlockResponseDto> updateBlock(@PathVariable String blockId,
        @Valid @RequestBody BlockRequestDto requestDto) {
        log.info("Rest request to update block : {}", requestDto);
        BlockVO blockVO = blockRestMapper.toBlockVO(requestDto);
        BlockVO updatedBlockVO = blockService.updateBlock(blockId, blockVO);

        return ResponseEntity.ok().body(blockRestMapper.toBlockResponseDto(updatedBlockVO));
    }

    @DeleteMapping("/{blockId}")
    @ApiOperation(value = "Delete the Block by id")
    public ResponseEntity<Void> deleteBlock(@PathVariable String blockId) {
        log.info("Rest request to delete block by id : {}", blockId);
        blockService.deleteById(blockId);
        return ResponseEntity.ok().build();
    }
}

package com.knighttodo.knighttodo.rest.dto.block.request;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBlockRequestDto {

    @NotBlank
    private String blockName;
}

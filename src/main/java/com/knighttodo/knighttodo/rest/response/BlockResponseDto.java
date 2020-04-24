package com.knighttodo.knighttodo.rest.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BlockResponseDto {

    private String id;

    private String blockName;

    private List<RoutineResponseDto> routines;
}

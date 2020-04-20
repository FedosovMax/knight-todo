package com.knighttodo.knighttodo.rest.dto.todoblock.response;

import com.knighttodo.knighttodo.gateway.privatedb.representation.Routine;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateTodoBlockResponseDto {

    private String id;

    private String blockName;

    private List<Routine> routines;
}

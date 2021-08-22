package com.knighttodo.knighttodo.rest.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineInstanceRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Hardness hardness;

    @NotNull
    private Scariness scariness;

    private boolean ready;

    private List<UUID> routineTodoInstancesIds;
}

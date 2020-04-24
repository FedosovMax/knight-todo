package com.knighttodo.knighttodo.rest.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scariness;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoutineRequestDto {

    @NotBlank
    private String name;

    @NotNull
    private Hardness hardness;

    @NotNull
    private Scariness scariness;

    private boolean ready;
}

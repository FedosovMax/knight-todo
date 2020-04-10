package com.knighttodo.knighttodo.rest.dto.routine.request;

import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.gateway.privatedb.representation.enums.Scaryness;

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
public class UpdateRoutineRequestDto {

    @NotBlank
    private String id;

    @NotBlank
    private String name;

    @NotNull
    private Hardness hardness;

    @NotNull
    private Scaryness scaryness;

    private boolean ready;
}

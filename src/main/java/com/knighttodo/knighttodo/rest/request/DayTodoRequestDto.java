package com.knighttodo.knighttodo.rest.request;

import com.knighttodo.knighttodo.service.privatedb.representation.enums.Hardness;
import com.knighttodo.knighttodo.service.privatedb.representation.enums.Scariness;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayTodoRequestDto {

    @NotBlank
    private String dayTodoName;

    @NotNull
    private Scariness scariness;

    @NotNull
    private Hardness hardness;

    private boolean ready;
}

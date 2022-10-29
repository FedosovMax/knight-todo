package com.knighttodo.todocore.rest.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DayRequestDto {

    @NotBlank
    private String dayName;
}

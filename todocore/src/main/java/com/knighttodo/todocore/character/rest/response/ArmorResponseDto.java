package com.knighttodo.todocore.character.rest.response;

import com.knighttodo.todocore.character.service.privatedb.representation.enums.ArmorType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
@EqualsAndHashCode(callSuper = true)
public class ArmorResponseDto extends ItemResponseDto {

    private ArmorType armorType;

    private Integer defence;
}

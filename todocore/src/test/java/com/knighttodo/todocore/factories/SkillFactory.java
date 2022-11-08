package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.character.service.privatedb.representation.Bonus;
import com.knighttodo.todocore.character.service.privatedb.representation.Skill;
import com.knighttodo.todocore.character.rest.request.SkillRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.util.Collections;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SkillFactory {

    private static final String SKILL_NAME = "Some super skill";
    private static final String SKILL_DESCRIPTION = "Some super skill description";
    private static final String UPDATED_SKILL_NAME = "Some updated super skill";
    private static final String UPDATED_SKILL_DESCRIPTION = "Some updated super skill description";

    public static SkillRequestDto createSkillRequestDto(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutName(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithNameConsistingOfSpaces(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name("    ")
            .description(SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutDescription(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithDescriptionConsistingOfSpaces(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description("    ")
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto createSkillRequestDtoWithoutBonusIds() {
        return SkillRequestDto.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .build();
    }

    public static Skill skillInstance(List<Bonus> bonuses) {
        return Skill.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .bonuses(bonuses)
            .build();
    }

    public static Skill skillInstance() {
        return Skill.builder()
            .name(SKILL_NAME)
            .description(SKILL_DESCRIPTION)
            .bonuses(Collections.emptyList())
            .build();
    }

    public static SkillRequestDto updateSkillRequestDto(List<String> bonusIds) {
        return SkillRequestDto.builder()
            .name(UPDATED_SKILL_NAME)
            .description(UPDATED_SKILL_DESCRIPTION)
            .bonusIds(bonusIds)
            .build();
    }

    public static SkillRequestDto updateSkillRequestDtoWithoutName() {
        return SkillRequestDto.builder()
            .description(UPDATED_SKILL_DESCRIPTION)
            .bonusIds(Collections.emptyList())
            .build();
    }

    public static SkillRequestDto updateSkillRequestDtoWithNameConsistingOfSpaces() {
        return SkillRequestDto.builder()
            .name("    ")
            .description(UPDATED_SKILL_DESCRIPTION)
            .bonusIds(Collections.emptyList())
            .build();
    }

    public static SkillRequestDto updateSkillRequestDtoWithoutDescription() {
        return SkillRequestDto.builder()
            .name(UPDATED_SKILL_NAME)
            .bonusIds(Collections.emptyList())
            .build();
    }

    public static SkillRequestDto updateSkillRequestDtoWithDescriptionConsistingOfSpaces() {
        return SkillRequestDto.builder()
            .name(UPDATED_SKILL_NAME)
            .description("    ")
            .bonusIds(Collections.emptyList())
            .build();
    }

    public static SkillRequestDto updateSkillRequestDtoWithoutBonusIds() {
        return SkillRequestDto.builder()
            .name(UPDATED_SKILL_NAME)
            .description(UPDATED_SKILL_DESCRIPTION)
            .build();
    }
}

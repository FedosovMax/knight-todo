package com.knighttodo.todocore.factories;

import com.knighttodo.todocore.character.service.privatedb.representation.Character;
import com.knighttodo.todocore.character.rest.request.CharacterRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CharacterFactory {

    public static final String CHARACTER_NAME = "Write integration tests";
    public static final String CHARACTER_USER_ID = "user_id";

    public static CharacterRequestDto characterRequestDtoInstance() {
        return CharacterRequestDto.builder().characterName(CHARACTER_NAME).build();
    }

    public static CharacterRequestDto characterRequestDtoWithNullNameInstance() {
        return CharacterRequestDto.builder().characterName(null).build();
    }

    public static CharacterRequestDto characterRequestDtoWithNameOfSpacesInstance() {
        return CharacterRequestDto.builder().characterName("   ").build();
    }

    public static Character createCharacterInstance() {
        return Character.builder().name(CHARACTER_NAME).userId(CHARACTER_USER_ID).build();
    }
}

package com.knighttodo.knighttodo.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
public enum Hardness {
    NOT_HARD("NOT_HARD"),
    HARD("HARD"),
    VERY_HARD("VERY_HARD"),
    EXTRAORDINARILY("EXTRAORDINARILY"),
    IMPOSSIBLE("IMPOSSIBLE");


    private String text;

    Hardness(String text) {
        this.text = text;
    }

    @JsonCreator
    public static Hardness fromText(String text){
        for(Hardness s : Hardness.values()){
            if(s.getText().equals(text)){
                return s;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type: " + text + "You can choose from: " + Arrays.toString(Hardness.values())
        );
    }

}

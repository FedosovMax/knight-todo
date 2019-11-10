package com.knighttodo.knighttodo.gateway.privatedb.representation.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
public enum Scaryness {
    NOT_SCARY("NOT_SCARY"),
    SCARY("SCARY"),
    VERY_SCARY("VERY_SCARY");

    private String text;

    Scaryness(String text) {
        this.text = text;
    }

    @JsonCreator
    public static Scaryness fromText(String text){
        for(Scaryness s : Scaryness.values()){
            if(s.getText().equals(text)){
                return s;
            }
        }
        throw new IllegalArgumentException(
                "Unknown enum type: " + text + "You can choose from: " + Arrays.toString(Scaryness.values())
        );
    }

}

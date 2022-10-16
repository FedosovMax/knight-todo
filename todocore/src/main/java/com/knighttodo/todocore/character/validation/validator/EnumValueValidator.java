package com.knighttodo.todocore.character.validation.validator;

import com.knighttodo.todocore.character.validation.annotation.ValidEnumValue;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EnumValueValidator implements ConstraintValidator<ValidEnumValue, CharSequence> {

    private List<String> acceptedValues;
    private String message;

    @Override
    public void initialize(ValidEnumValue annotation) {
        acceptedValues = Stream.of(annotation.enumClass().getEnumConstants())
            .map(Enum::name)
            .collect(Collectors.toList());
        message = String.format("Value is not valid. It should be one of values : %s",
            String.join("|", acceptedValues));
    }

    @Override
    public boolean isValid(CharSequence value, ConstraintValidatorContext context) {
        if (value != null && acceptedValues.contains(value.toString())) {
            return true;
        }
        setInvalidRarityMessage(context, message);
        return false;
    }

    private void setInvalidRarityMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }
}

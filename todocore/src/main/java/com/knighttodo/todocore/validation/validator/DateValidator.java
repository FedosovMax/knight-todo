package com.knighttodo.todocore.validation.validator;

import com.knighttodo.todocore.exception.DateBadFormatException;
import com.knighttodo.todocore.validation.annotation.ValidDate;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class DateValidator implements ConstraintValidator<ValidDate, String> {

    private Pattern pattern;

    @Override
    public void initialize(ValidDate validDate) {
        try {
            pattern = Pattern.compile(validDate.regexp());
        } catch (PatternSyntaxException ex) {
            throw new IllegalArgumentException("Given regex is invalid", ex);
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Matcher m = pattern.matcher(value);

        if (m.matches()) {
            return true;
        } else {
            throw new DateBadFormatException("Invalid date format");
        }
    }
}

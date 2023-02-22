package com.knighttodo.todocore.validation.validator;

import com.knighttodo.todocore.validation.annotation.ValidDate;
import lombok.extern.slf4j.Slf4j;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

@Slf4j
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
        if (value == null) {
            return true;
        }
        Matcher m = pattern.matcher(value);

        try {
            if (m.matches()) {
                return true;
            } else {
                log.error("Date in bad format : " + value);
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            return false;
        }
    }
}

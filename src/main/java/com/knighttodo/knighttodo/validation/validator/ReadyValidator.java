package com.knighttodo.knighttodo.validation.validator;

import com.knighttodo.knighttodo.validation.annotation.ValidReady;
import java.util.List;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class ReadyValidator implements ConstraintValidator<ValidReady, String> {

    private final List<String> validStatuses = List.of("true", "false");

    @Override
    public void initialize(ValidReady validReady) {
    }

    @Override
    public boolean isValid(String readyStatus, ConstraintValidatorContext constraintValidatorContext) {
        try {
            if (validStatuses.contains(readyStatus.toLowerCase())) {
                return true;
            } else {
                throw new IllegalArgumentException();
            }
        } catch (IllegalArgumentException ex) {
            setValidationResultMessage(constraintValidatorContext,
                String.format("Your readiness status: \"%s\", isn't valid", readyStatus));
            return false;
        }
    }

    private void setValidationResultMessage(ConstraintValidatorContext context, String message) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message).addConstraintViolation();
    }

}

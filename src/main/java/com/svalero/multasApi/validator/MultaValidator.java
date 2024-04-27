package com.svalero.multasApi.validator;

import com.svalero.multasApi.domain.Multa;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

public class MultaValidator implements Validator {

    @Override
    public boolean supports(Class<?> clazz) {
        return Multa.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "matricula", "field.required");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "velocidadAlcanzada", "field.required");
    }
}

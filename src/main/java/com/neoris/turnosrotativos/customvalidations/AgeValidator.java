package com.neoris.turnosrotativos.customvalidations;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;
import java.time.Period;

public class AgeValidator implements ConstraintValidator<Age, LocalDate> {

    private int minAge;

    @Override
    public void initialize(Age age) {
        minAge = age.value();
    }

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        int age = Period.between(value, LocalDate.now()).getYears();
        return age >= minAge;
    }
}

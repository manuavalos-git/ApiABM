package com.neoris.turnosrotativos.customvalidations;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = AgeValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Age {
    String message() default "La edad del empleado no puede ser menor a {value} años.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    int value() default 18;
}

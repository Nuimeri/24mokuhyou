package com.circleaf.circleaf_api.validator.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = com.circleaf.circleaf_api.validator.Past13YearsValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface Past13Years {

    String message() default "日付は13年前以前である必要があります。";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

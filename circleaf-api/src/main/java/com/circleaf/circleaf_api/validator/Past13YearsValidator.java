package com.circleaf.circleaf_api.validator;

import com.circleaf.circleaf_api.validator.annotation.Past13Years;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDate;

public class Past13YearsValidator implements ConstraintValidator<Past13Years, LocalDate> {

    @Override
    public boolean isValid(LocalDate value, ConstraintValidatorContext context) {
        if (value == null) {
            return true; // nullの場合は他のアノテーション（例: @NotNull）に任せる
        }

        // 現在の日付から13年前を計算
        LocalDate thirteenYearsAgo = LocalDate.now().minusYears(13);

        // 入力値が13年前以前ならtrue
        return value.isBefore(thirteenYearsAgo);
    }
}

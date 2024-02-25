package com.account.yomankum.common.annotation;

import com.account.yomankum.common.annotation.constraint.PasswordValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Constraint(validatedBy = {PasswordValidator.class})
@Target({FIELD})
@Retention(RUNTIME)
public @interface Password {
    String message() default "비밀번호 형식이 다릅니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

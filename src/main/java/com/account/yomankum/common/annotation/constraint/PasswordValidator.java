package com.account.yomankum.common.annotation.constraint;

import com.account.yomankum.common.annotation.Password;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;

@Component
public class PasswordValidator implements ConstraintValidator<Password, String> {

    private static final int MIN_SIZE = 6;
    private static final int MAX_SIZE = 20;
    private static final String regexPassword = "^[a-zA-Z0-9]{"+MIN_SIZE+","+MAX_SIZE+"}$";
    @Override
    public boolean isValid(String password, ConstraintValidatorContext context) {

        if (!StringUtils.hasText(password) ||
                !password.matches(regexPassword)
        ) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(
                            MessageFormat.format("{0}자 이상의 {1}자 이하의 숫자, 영 대소문자로 이루어진 비밀번호가 필요합니다.", MIN_SIZE, MAX_SIZE))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

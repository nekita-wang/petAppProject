package com.petlife.platform.common.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message() default "两次输入的密码不一致";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
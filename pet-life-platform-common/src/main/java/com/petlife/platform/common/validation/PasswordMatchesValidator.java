package com.petlife.platform.common.validation;

import com.petlife.platform.common.pojo.dto.StepRegisterDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, StepRegisterDTO> {
    @Override
    public boolean isValid(StepRegisterDTO dto, ConstraintValidatorContext context) {
        if (dto == null) return true; // 交给@NotNull等注解处理
        String pwd = dto.getPassword();
        String confirm = dto.getPasswordConfirm();
        if (pwd == null || confirm == null) return false;
        return pwd.equals(confirm);
    }
}
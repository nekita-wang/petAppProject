package com.petlife.platform.common.pojo.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class SendCodeDTO {
    private String phone;
}

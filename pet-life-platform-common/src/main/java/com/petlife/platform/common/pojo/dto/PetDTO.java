package com.petlife.platform.common.pojo.dto;

import lombok.Data;

@Data
public class PetDTO {
    private Long userId;
    private String nickname;
    private String type; // 猫/狗
    private String breed;
    private String avatar;
    private Byte gender;
    private String birthday;
    private String arrivalDate;
    private Boolean isSterilized;
}

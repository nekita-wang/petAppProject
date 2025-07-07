package com.petlife.platform.common.utils;

import java.time.LocalDate;

public class DateValidationUtils {

    public static void validatePetDateLogic(LocalDate param1, LocalDate param2,String msg) {

        if (param1.isAfter(param2)) {
            throw new RuntimeException(msg);
        }
    }
}

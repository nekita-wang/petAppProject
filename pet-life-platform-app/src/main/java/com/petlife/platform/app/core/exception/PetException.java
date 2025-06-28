package com.petlife.platform.app.core.exception;


import com.petlife.platform.app.core.api.ServiceCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * 自定义异常
 */
public class PetException extends RuntimeException {

    private ServiceCode serviceCode;

    public PetException(ServiceCode serviceCode) {
        this.serviceCode = serviceCode;
    }
    public PetException(int code, String msg) {
        this.serviceCode = new ServiceCode() {
            @Override
            public int getCode() {
                return code;
            }
            @Override
            public String getMsg() {
                return msg;
            }
        };
    }
}

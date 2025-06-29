package com.petlife.platform.common.core.exception;


import com.petlife.platform.common.core.api.ServiceCode;
import lombok.Getter;
import lombok.Setter;

/**
 * 自定义异常
 */
@Getter
@Setter
public class PetException extends RuntimeException {

    private ServiceCode serviceCode;

    public PetException(ServiceCode serviceCode) {
        super(serviceCode.getMsg());
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

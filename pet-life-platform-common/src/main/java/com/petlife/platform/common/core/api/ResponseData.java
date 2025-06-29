package com.petlife.platform.common.core.api;

import lombok.Data;

@Data
public class ResponseData<T>{
    /**
     * 响应码(默认200正常响应)
     */
    private int code = 200;
    /**
     * 响应信息(默认OK)
     */
    private String msg = "SUCESS";
    /**
     * 响应结果(默认成功)
     */
    private boolean success = true;
    /**
     * 响应数据
     */
    private T data;

    private ResponseData(){}

    private ResponseData(T data){
        this.data = data;
    }

    /**
     * 失败响应
     * @param serviceCode
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> error(ServiceCode serviceCode){
        ResponseData responseData = new ResponseData();
        responseData.setCode(serviceCode.getCode());
        responseData.setMsg(serviceCode.getMsg());
        responseData.setSuccess(false);
        return responseData;
    }
    /**
     * 失败响应
     * @param code 响应编码
     * @param msg 响应信息
     * @return
     */
    public static <T> ResponseData<T> error(int code, String msg){
        ResponseData responseData = new ResponseData();
        responseData.setCode(code);
        responseData.setMsg(msg);
        responseData.setSuccess(false);
        return responseData;
    }

    /**
     * 成功响应
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> ok(){
        return new ResponseData<>();
    }

    /**
     * 成功响应：带data数据
     * @param <T>
     * @return
     */
    public static <T> ResponseData<T> ok(T data){
        return new ResponseData<>(data);
    }

}

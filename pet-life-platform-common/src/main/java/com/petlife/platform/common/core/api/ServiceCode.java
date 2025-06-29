package com.petlife.platform.common.core.api;

public interface ServiceCode {
    /**
     * 响应编码
     * @return
     */
    int getCode();

    /**
     * 响应描述
     * @return
     */
    String getMsg();
}

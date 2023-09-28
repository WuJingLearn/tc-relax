package org.javaboy.tcrelax.common;

import lombok.Data;

/**
 * @author:majin.wj
 */
@Data
public class TcResult<T> {

    private T data;
    private boolean success;
    private String errorCode;
    private String errorMsg;


    public static <T> TcResult<T> success(T data) {
        TcResult<T> result = new TcResult<>();
        result.setData(data);
        result.setSuccess(true);
        return result;
    }


    public static <T> TcResult<T> fail(String errorMsg) {
        TcResult<T> result = new TcResult<>();
        result.setErrorMsg(errorMsg);
        result.setSuccess(false);
        return result;
    }

    public static <T> TcResult<T> fail(String errorCode,String errorMsg) {
        TcResult<T> result = new TcResult<>();
        result.setErrorCode(errorCode);
        result.setErrorMsg(errorMsg);
        result.setSuccess(false);
        return result;
    }
}

package org.javaboy.relax.draw.exception;


import org.javaboy.relax.draw.enums.LotteryResultEnum;

/**
 * @author:majin.wj
 */
public class LotteryException extends RuntimeException{


    private LotteryResultEnum result = LotteryResultEnum.SYSTEM_ERROR;

    public LotteryException(LotteryResultEnum result){
        super(result.getDesc());
        this.result = result;
    }

    public String getErrorCode(){
        return result.getCode();
    }
    @Override
    public String getMessage() {
        return result.getDesc();
    }


}

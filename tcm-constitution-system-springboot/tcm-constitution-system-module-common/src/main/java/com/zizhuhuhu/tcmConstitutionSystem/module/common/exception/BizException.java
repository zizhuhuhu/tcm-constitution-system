package com.zizhuhuhu.tcmConstitutionSystem.module.common.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BizException extends RuntimeException{
    private String errorCode;
    private String errMessage;
    public BizException(BaseExceptionInterface baseExceptionInterface){
        this.errorCode = baseExceptionInterface.getErrorCode();
        this.errMessage = baseExceptionInterface.getErrorMessage();
    }
}

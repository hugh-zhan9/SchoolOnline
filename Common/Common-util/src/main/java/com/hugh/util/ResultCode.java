package com.hugh.util;

/**
 * Created by hugh on 2021/1/6
 */
public enum ResultCode {
    SUCCESS(20000), ERROR(200001);

    private int resultCode;

    private ResultCode(int resultCode){
        this.resultCode = resultCode;
    }
}

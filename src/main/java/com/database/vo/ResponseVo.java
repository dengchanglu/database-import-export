package com.database.vo;

/**
 * Created by perfection on 17-2-28.
 */
public class ResponseVo {

    private Object obj;

    private boolean isSuccess;

    public ResponseVo(){}

    public ResponseVo(Object obj,boolean isSuccess){
        this.obj = obj;
        this.isSuccess = isSuccess;
    }
}

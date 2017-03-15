package com.database.exceptions;

/**
 * Created by perfection on 17-2-28.
 */
public class DIEException extends RuntimeException{

    private static final long serialVersionUID = -5875371379845226068L;

    public static final DIEException SYSTEM_ERROR = new DIEException(1000, "系统异常");

    public static final DIEException USER_INFO_LOGIN_ACCOUNT_NULL = new DIEException(1101, "登陆账号为空");

    public static final DIEException USER_INFO_LOGIN_PASSWORD_NULL = new DIEException(1102, "登陆密码为空");

    /**
     * 异常信息
     */
    protected String msg;

    /**
     * 具体异常码
     */
    protected int code;

    public DIEException(Integer code, String msg) {
        this.msg = msg;
        this.code = code;
    }

    public DIEException() {
    }

    public String getMsg() {
        return msg;
    }

    public int getCode() {
        return code;
    }
}

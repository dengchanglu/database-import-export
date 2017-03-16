package com.database.exceptions;

/**
 * Created by 邓昌路 on 17-2-28.
 */
public class DIEException extends RuntimeException{

    private static final long serialVersionUID = -5875371379845226068L;

    public static final DIEException SYSTEM_ERROR = new DIEException(1000, "系统异常");

    public static final DIEException USER_INFO_LOGIN_PARAM_NULL = new DIEException(1101, "登陆参数异常");

    public static final DIEException USER_INFO_REGISTER_NULL = new DIEException(1102, "注册参数异常");

    public static final DIEException USER_INFO_LOGIN_QUERY_NULL = new DIEException(1103, "账号不存在");

    public static final DIEException USER_INFO_LOGIN_QUERY_ERROR = new DIEException(1104, "密码错误");

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

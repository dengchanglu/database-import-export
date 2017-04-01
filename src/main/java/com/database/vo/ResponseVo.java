package com.database.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by perfection on 17-2-28.
 */
@Data
public class ResponseVo {

    private Object data;

    private Integer code;

    private String msg;

    public ResponseVo(){}

    public ResponseVo(Object data,Integer code,String msg){
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public ResponseVo(Object data){
        this.data = data;
        this.code = 200;
        this.msg = "处理成功";
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}

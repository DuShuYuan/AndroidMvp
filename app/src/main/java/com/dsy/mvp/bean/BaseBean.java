package com.dsy.mvp.bean;

import java.io.Serializable;

public class BaseBean implements Serializable {

    /**
     * 必包含以下两个字段（可根据json定义自行调整）
     *
     * code : 0
     * msg : 请求成功
     */

    private int code;
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}

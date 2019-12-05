package com.dsy.mvp.net;

public class DataException extends Exception {

    String msg;
    int code;

    public DataException(){
        super();
    }
    public DataException(int code,String message){
        super(message);
        this.code=code;
        this.msg=message;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}

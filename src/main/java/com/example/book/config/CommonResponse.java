package com.example.book.config;

import java.io.Serializable;

public class CommonResponse implements Serializable {

    private static final long serialVersionUID = -4517038395887682548L;


    public CommonResponse(String code,String msg,Object result){
        this.code = code;
        this.msg = msg;
        this.result = result;
    }
    public CommonResponse(String code,String msg){
      this(code,msg,null);
    }
    public CommonResponse(String msg,Object result){
        this("0",msg,result);
    }
    public CommonResponse(String msg){
        this("0",msg,null);
    }
    public CommonResponse(Object result){
        this("0","success",result);
    }
    public CommonResponse(){
        this("0","success",null);
    }
    private String code;
    private String msg;
    private Object result;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }
}

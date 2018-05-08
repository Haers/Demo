package com.acticitytest.demo.entity;

public class HttpResult<T> {
    private  int status;
    private String msg;
    private T data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getStatus() {

        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        sb.append("status="+status+",msg="+msg);
        if(null != data){
            sb.append(",data="+data.toString());
        }
        return sb.toString();
    }
}

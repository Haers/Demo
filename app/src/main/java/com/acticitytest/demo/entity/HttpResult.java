package com.acticitytest.demo.entity;

public class HttpResult<T> {
    private int status;
    private int type;
    private String info;
    private T data;

    public int getType(int type){
        return type;
    }
    public void setType(int type){
        this.type = type;
    }
    public String getInfo(){
        return info;
    }
    public void setInfo(String info){
        this.info = info;
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
        sb.append("status="+status+",msg="+info);
        if(null != data){
            sb.append(",data="+data.toString());
        }
        return sb.toString();
    }
}

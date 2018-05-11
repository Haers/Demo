package com.acticitytest.demo.entity;

public class Message {
    //class message
    private int id;
    private String senderId;
    private String sendDate;
    private String sendTime;
    private String msg;
    private String fetchLocation;
    private String sendLocation;
    private boolean isCaught;
    private String receiverId;
    private boolean isDone;

    public int getId(){
        return id;
    }

    public void setId(int id){
        this.id = id;
    }

    public String getSenderId(){
        return senderId;
    }

    public void setSenderId(String senderId){
        this.senderId = senderId;
    }


    public String getSendDate(){
        return sendDate;
    }

    public void setSendDate(String date){
        this.sendDate = date;
    }

    public String getSendTime(){
        return sendTime;
    }

    public void setSendTime(String time){
        this.sendTime = time;
    }

    public String getMsg(){
        return msg;
    }

    public void setMsg(String msg){
        this.msg = msg;
    }

    public String getFetchLocation(){
        return fetchLocation;
    }

    public void setFetchLocation(String fetchLocation){
        this.fetchLocation = fetchLocation;
    }

    public String getSendLocation(){
        return sendLocation;
    }

    public void setSendLocation(String sendLocation){
        this.sendLocation = sendLocation;
    }

    public boolean isCaught(){
        return isCaught;
    }

    public void setCaught(boolean isCaught){
        this.isCaught = isCaught;
    }

    public String getReceiverId(){
        return receiverId;
    }

    public void setReceiverId(String receiverId){
        this.receiverId = receiverId;
    }

    public boolean isDone(){
        return isDone;
    }

    public void setDone(boolean isDone){
        this.isDone = isDone;
    }
}

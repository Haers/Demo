package com.acticitytest.demo.http.presenter;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import java.util.List;
import rx.Subscriber;
import rx.Observable;

public class MessagePresenter extends HttpMethods {
    //get message data from remote server
    public static void showAllMessage(Subscriber<List<Message>> subscriber){
        Observable<List<Message>> observable = messageService.getAllMessage()
                .map(new HttpResultFunc<List<Message>>());
        toSubscribe(observable, subscriber);
    }

    public static void sendMessage(Subscriber<HttpResult<Message>> subscriber, String senderId,
                                   String msg, String sendLocation, String fetchLocation){
        Observable<HttpResult<Message>> observable = messageService
                .sendMessage(senderId, msg, sendLocation, fetchLocation);
        toSubscribe(observable, subscriber);
    }

    public static void getMessageBySenderId(Subscriber<List<Message>> subscriber, String senderId){

        Observable<List<Message>> observable = messageService.getMessageBySenderId(senderId)
                .map(new HttpResultFunc<List<Message>>());
        toSubscribe(observable, subscriber);
    }

    public static void getMessageByReceiverId(Subscriber<List<Message>> subscriber,
                                              String receiverId){
        Observable<List<Message>> observable = messageService.getMessageByReceiverId(receiverId)
                .map(new HttpResultFunc<List<Message>>());
        toSubscribe(observable, subscriber);
    }

    public static void modifyMessage(Subscriber<HttpResult<Message>> subscriber, int id, String msg,
            String sendLocation, String fetchLocation){
        Observable<HttpResult<Message>> observable = messageService.modifyMessage(id, msg,
                sendLocation, fetchLocation);
        toSubscribe(observable, subscriber);
    }

    public static void deleteMessage(Subscriber<HttpResult<Message>> subscriber, int id){
        Observable<HttpResult<Message>> observable = messageService.deleteMessage(id);
        toSubscribe(observable, subscriber);
    }

    public static void caughtMessage(Subscriber<HttpResult<Message>> subscriber,
                                    int id, String receiverId){
        Observable<HttpResult<Message>> observable = messageService.caughtMessage(id, receiverId);
        toSubscribe(observable, subscriber);
    }

    public static void doneMessage(Subscriber<HttpResult<Message>> subscriber, int id){
        Observable<HttpResult<Message>> observable = messageService.doneMessage(id);
        toSubscribe(observable, subscriber);
    }
}

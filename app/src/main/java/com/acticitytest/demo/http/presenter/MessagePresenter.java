package com.acticitytest.demo.http.presenter;

import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import rx.Subscriber;
import rx.Observable;

public class MessagePresenter extends HttpMethods {
    //get message data from remote server
    public static void showMessage(Subscriber<Message> subscriber){
        Observable<Message> observable = messageService.getMessage()
                .map(new HttpResultFunc<Message>());
        toSubscribe(observable, subscriber);
    }

    public static void sendMessage(Subscriber<Message> subscriber, int id, String stuNum, String msg){
        Observable<Message> observable = messageService.sendMessage(id, stuNum, msg)
                .map(new HttpResultFunc<Message>());
        toSubscribe(observable, subscriber);
        messageService.sendMessage(id, stuNum, msg);
    }

}

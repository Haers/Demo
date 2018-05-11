package com.acticitytest.demo.http.presenter;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import java.util.List;
import rx.Subscriber;
import rx.Observable;

public class MessagePresenter extends HttpMethods {
    //get message data from remote server
    public static void showMessage(Subscriber<List<Message>> subscriber){
        Observable<List<Message>> observable = messageService.getMessage()
                .map(new HttpResultFunc<List<Message>>());
        toSubscribe(observable, subscriber);
    }

    public static void sendMessage(Subscriber<HttpResult<Message>> subscriber, String senderId,
                                   String msg, String sendLocation, String fetchLocation){
        Observable<HttpResult<Message>> observable = messageService
                .sendMessage(senderId, msg, sendLocation, fetchLocation);
        toSubscribe(observable, subscriber);
    }

}

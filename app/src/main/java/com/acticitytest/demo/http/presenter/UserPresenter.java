package com.acticitytest.demo.http.presenter;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.entity.User;

import java.util.List;

import rx.Observable;
import rx.Subscriber;

public class UserPresenter extends HttpMethods{

    public static void addUser(Subscriber<HttpResult<User>> subscriber, String stuNum){
        Observable<HttpResult<User>> observable = userService.addUser(stuNum);
        toSubscribe(observable, subscriber);
    }

    public static void modifyUser(Subscriber<HttpResult<User>> subscriber,
                                  String stuNum, String name, boolean gender,
                                  String defaultLocation, String telephone, String pay){
        Observable<HttpResult<User>> observable = userService.modifyUser(stuNum, name,
                gender, defaultLocation, telephone, pay);
        toSubscribe(observable, subscriber);
    }

    public static void getUser(Subscriber<List<User>> subscriber, String stuNum){
        Observable<List<User>> observable = userService.getUser(stuNum)
                .map(new HttpResultFunc<List<User>>());
        toSubscribe(observable, subscriber);
    }
}

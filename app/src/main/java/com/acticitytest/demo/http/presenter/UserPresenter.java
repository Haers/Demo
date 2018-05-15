package com.acticitytest.demo.http.presenter;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.http.HttpMethods;
import com.acticitytest.demo.entity.User;
import rx.Observable;
import rx.Subscriber;

public class UserPresenter extends HttpMethods{

    public static void addUser(Subscriber<HttpResult<User>> subscriber, String stuNum){
        Observable<HttpResult<User>> observable = userService.addUser(stuNum);
        toSubscribe(observable, subscriber);
    }

    public static void modifyUser(Subscriber<HttpResult<User>> subscriber,
                                  String stuNum, String name, int gender,
                                  String defaultLocation, String telephone, String pay){
        Observable<HttpResult<User>> observable = userService.modifyUser(stuNum, name,
                gender, defaultLocation, telephone, pay);
        toSubscribe(observable, subscriber);
    }
}

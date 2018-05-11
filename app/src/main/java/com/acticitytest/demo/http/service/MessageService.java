package com.acticitytest.demo.http.service;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;

import java.util.List;

import rx.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {
    //get all message stored in db
    @GET("alpha5/servlet/MessageGetServlet")
    Observable<HttpResult<List<Message>>> getMessage();

    @GET("alpha5/servlet/MessageAddServlet")
    Observable<HttpResult<Message>> sendMessage(
            @Query("senderId") String senderId,
            @Query("msg") String msg,
            @Query("sendLocation") String sendLocation,
            @Query("fetchLocation") String fetchLocation
    );
}

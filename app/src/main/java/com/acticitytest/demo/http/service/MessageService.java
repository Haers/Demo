package com.acticitytest.demo.http.service;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {
    //get all message stored in db
    @GET("alpha5/servlet/MessageGetServlet")
    Observable<HttpResult<List<Message>>> getMessage();

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageAddServlet")
    Observable<HttpResult<Message>> sendMessage(
            @Field("senderId") String senderId,
            @Field("msg") String msg,
            @Field("sendLocation") String sendLocation,
            @Field("fetchLocation") String fetchLocation
    );
}

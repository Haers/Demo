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
    @POST("alpha5/servlet/MessageGetServlet")
    Observable<HttpResult<List<Message>>> getAllMessage();
    //add message to db
    @FormUrlEncoded
    @POST("alpha5/servlet/MessageAddServlet")
    Observable<HttpResult<Message>> sendMessage(
            @Field("senderId") String senderId,
            @Field("msg") String msg,
            @Field("sendLocation") String sendLocation,
            @Field("fetchLocation") String fetchLocation
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageGetBySenderServlet")
    Observable<HttpResult<List<Message>>> getMessageBySenderId(
            @Field("senderId") String senderId
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageGetByReceiverServlet")
    Observable<HttpResult<List<Message>>> getMessageByReceiverId(
            @Field("receiverId") String receiverId
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageModifyServlet")
    Observable<HttpResult<Message>> modifyMessage(
            @Field("id") int id,
            @Field("msg") String msg,
            @Field("sendLocation") String sendLocation,
            @Field("fetchLocation") String fetchLocation
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageDeleteServlet")
    Observable<HttpResult<Message>> deleteMessage(
            @Field("id") int id
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageCaughtServlet")
    Observable<HttpResult<Message>> caughtMessage(
            @Field("id") int id,
            @Field("receiverId") String receiverId
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/MessageDoneServlet")
    Observable<HttpResult<Message>> doneMessage(
            @Field("id") int id
    );
}

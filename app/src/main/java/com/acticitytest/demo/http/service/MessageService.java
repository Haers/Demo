package com.acticitytest.demo.http.service;

import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.Message;
import rx.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface MessageService {
    //get message
    @GET("MyJsp/servlet/DemoServlet")
    Observable<HttpResult<Message>> getMessage();

    @GET("MyJsp/servlet/DemoServlet")
    Observable<HttpResult<Message>> sendMessage(
            @Query("id") int id,
            @Query("stuNum") String stuNum,
            @Query("msg") String msg
    );
}

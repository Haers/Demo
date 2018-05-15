package com.acticitytest.demo.http.service;


import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.entity.User;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface UserService {

    @GET("alpha5/servlet/UserAddServlet")
    Observable<HttpResult<User>> addUser(
            @Query("stuNum") String stuNum
    );

    @FormUrlEncoded
    @POST("alpha5/servlet/UserModifyServlet")
    Observable<HttpResult<User>> modifyUser(
            @Field("stuNum") String stuNum,
            @Field("name") String name,
            @Field("gender") int gender,
            @Field("defaultLocation") String defaultLocation,
            @Field("telephone") String telephone,
            @Field("pay") String pay
    );
}

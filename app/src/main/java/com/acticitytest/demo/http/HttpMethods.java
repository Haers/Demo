package com.acticitytest.demo.http;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import com.acticitytest.demo.entity.HttpResult;
import com.acticitytest.demo.http.service.MessageService;
import com.acticitytest.demo.http.service.UserService;

public class HttpMethods {
    protected static final String BASE_URL = "http://120.78.209.114:8080/";
    private static final int DEFAULT_TIMEOUT = 5;
    protected static final String TAG = "HttpMethods";
    private Retrofit retrofit;
    private static HttpMethods mInstance;
    protected static MessageService messageService;
    protected static UserService userService;

    public HttpMethods(){
        if(mInstance == null){
            //CookieJarImpl cookieJar = new CookieJarImpl(new MemoryCookieStore());
            OkHttpClient okHttpClient = new OkHttpClient.Builder()
                    //.cookieJar(cookieJar)
                    .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                    .build();
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .client(okHttpClient)
                    .build();
            messageService = retrofit.create(MessageService.class);
            userService = retrofit.create(UserService.class);
        }
    }

    public static HttpMethods getInstance(){
        if(mInstance == null){
            synchronized (HttpMethods.class){
                if(mInstance == null){
                    mInstance = new HttpMethods();
                }
            }
        }
        return mInstance;
    }

    public static class HttpResultFunc<T> implements Func1<HttpResult<T>, T> {
        @Override
        public T call(HttpResult<T> httpResult){
            Log.i(TAG,"status:"+httpResult.getStatus());
            Log.i(TAG,"info:"+httpResult.getInfo());
            Log.i(TAG,"data:"+httpResult.getData());
            return httpResult.getData();
        }
    }

    public static <T> void toSubscribe(Observable<T> o, Subscriber<T> s){
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }
}

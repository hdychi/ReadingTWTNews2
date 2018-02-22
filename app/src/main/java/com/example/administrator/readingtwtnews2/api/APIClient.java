package com.example.administrator.readingtwtnews2.api;

import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;


import okhttp3.OkHttpClient;
import okhttp3.internal.platform.Platform;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.api.API;
import com.orhanobut.logger.Logger;
import static okhttp3.internal.platform.Platform.INFO;

/**
 * Created by Administrator on 2017/6/17.
 */

public class APIClient {
    protected Retrofit mRetrofit;
    private API mService;
    public APIClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(message->{
            if (message.startsWith("{")){
                Logger.json(message);

            }else {
                Platform.get().log(INFO, message, null);
            }
        });

        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder()


                .retryOnConnectionFailure(true)

                .connectTimeout(30, TimeUnit.SECONDS)

                .build();
        mRetrofit = new Retrofit.Builder()

                .baseUrl("http://open.twtstudio.com/api/v1/news/")

                .client(client)

                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())

                .addConverterFactory(GsonConverterFactory.create())

                .build();
        mService = mRetrofit.create(API.class);
    }
    public void getNews(int type, int page, Subscriber subscriber){

        Observable<NewsBean> observable = mService.getNews(type,page).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
        observable.subscribe(subscriber);
    }
    public void getConcrete(int index, Subscriber subscriber){
        Observable observable = mService.getConcrte(index).map(concreteNews ->concreteNews.getData()).observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io());
        observable.subscribe(subscriber);
    }
}

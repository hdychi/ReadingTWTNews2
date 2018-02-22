package com.example.administrator.readingtwtnews2.api;

import com.example.administrator.readingtwtnews2.Models.ConcreteNews;
import com.example.administrator.readingtwtnews2.Models.NewsBean;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Administrator on 2017/5/31.
 */

public interface  API {
    @GET("{type}/page/{pageNum}")
   Observable<NewsBean> getNews(@Path("type")int type, @Path("pageNum")int pageNum);
    @GET("{index}")
    Observable<ConcreteNews>getConcrte(@Path("index")int index);
}

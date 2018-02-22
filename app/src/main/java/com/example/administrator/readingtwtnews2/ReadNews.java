package com.example.administrator.readingtwtnews2;

import android.content.Intent;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.readingtwtnews2.Models.ConcreteNews;
import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.api.APIClient;

import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import java.util.List;


import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2017/6/17.
 */

public class ReadNews extends AppCompatActivity {
    private WebView webView;
    private TextView title;
    private TextView newsCome;
    private TextView gonggao;
    private TextView shengao;
    private TextView visCount;
    private String str;
    private NewsBean.DataBean bean;
    private boolean hasCollected = false;
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.read_news);
        webView = (WebView)findViewById(R.id.webview);
        title = (TextView)findViewById(R.id.concreteTitle);
        newsCome = (TextView)findViewById(R.id.concreteNEWSCOME);
        gonggao = (TextView) findViewById(R.id.concreteGONGGAO);
        shengao = (TextView)findViewById(R.id.concreteSHENGAO);
        visCount = (TextView)findViewById(R.id.visCount);
        Intent intent = getIntent();
        Bundle bundle=intent.getExtras();//.getExtras()得到intent所附带的额外数据 
        bean = (NewsBean.DataBean)bundle.getSerializable("object");
        getData(bean);
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边

                return false;
            }

        });
        webView.getSettings().setJavaScriptEnabled(true);
        initialViews();

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //加载菜单文件
        getMenuInflater().inflate(R.menu.read_news_menu, menu);
        DataBaseHelper mHelper = new DataBaseHelper();
        Observable<Boolean> observable =  Observable.just(mHelper).map(dataBaseHelper ->dataBaseHelper.queryLove())
                                .flatMap(dataBeen -> Observable.from(dataBeen))
                                .map(dataBean -> dataBean.getIndex())
                                .toList()
                                .map(integers ->integers.contains(bean.getIndex()))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());

        Subscriber<Boolean> subscriber = new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean aBoolean) {
                if(aBoolean){menu.getItem(0).setTitle("取消收藏");hasCollected=true;}
                else{menu.getItem(0).setTitle("收藏");hasCollected=false;}
            }
        };
        observable.subscribe(subscriber);
        return true;
    }
    public void initialViews() {

        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mToolbar.setTitle("新闻详情");
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mToolbar.setNavigationOnClickListener(v -> {finish();});
        mToolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.collect) {
                    DataBaseHelper mHelper = new DataBaseHelper();
                    if (!hasCollected) {
                        rx.Observable<Boolean> observable = rx.Observable.just(mHelper)
                                .map(dataBaseHelper -> dataBaseHelper.writeLove(bean))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());
                        observable.subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.i("写入","异常");
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                Log.i("写入","成功");
                                Toast.makeText(ReadNews.this, "收藏成功", Toast.LENGTH_SHORT).show();
                                item.setTitle("取消收藏");hasCollected=true;
                            }
                        });
                    }
                    else{
                        rx.Observable<Boolean> observable = rx.Observable.just(mHelper)
                                .map(dataBaseHelper -> dataBaseHelper.deleteLove(bean.getIndex()))
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribeOn(Schedulers.io());
                        observable.subscribe(new Subscriber<Boolean>() {
                            @Override
                            public void onCompleted() {

                            }

                            @Override
                            public void onError(Throwable e) {
                                e.printStackTrace();
                                Log.i("删除","异常");
                            }

                            @Override
                            public void onNext(Boolean aBoolean) {
                                Log.i("删除","成功");
                                Toast.makeText(ReadNews.this, "取消收藏成功", Toast.LENGTH_SHORT).show();
                                item.setTitle("收藏");
                                hasCollected=false;
                            }
                        });
                    }


                }
                return  false;
            }
        });




    }

    public void getData(NewsBean.DataBean bean){


        Subscriber<ConcreteNews.DataBean> subscriber = new Subscriber<ConcreteNews.DataBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
               e.printStackTrace();

            }

            @Override
            public void onNext(ConcreteNews.DataBean concreteNews) {
                if (concreteNews!= null) {
                    title.setText(concreteNews.getSubject());
                    webView.loadDataWithBaseURL(null,"<html>"+"<body>"+concreteNews.getContent()+"</body>"+"</html>","text/html","utf-8",null);
                    newsCome.setText("新闻来源："+concreteNews.getNewscome());
                    gonggao.setText("供稿人："+concreteNews.getGonggao());
                    shengao.setText("审稿人："+concreteNews.getShengao());
                    visCount.setText("访问数："+concreteNews.getVisitcount()+"");
                }
            }
        };
        APIClient client = new APIClient();
        client.getConcrete(bean.getIndex(),subscriber);
    }

}

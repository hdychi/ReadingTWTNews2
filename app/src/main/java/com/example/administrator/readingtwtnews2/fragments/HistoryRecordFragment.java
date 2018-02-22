package com.example.administrator.readingtwtnews2.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import com.example.administrator.readingtwtnews2.DataBaseHelper;
import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.OnItemClickListener;
import com.example.administrator.readingtwtnews2.R;
import com.example.administrator.readingtwtnews2.ReadNews;
import com.example.administrator.readingtwtnews2.adapters.RecyclerAdapter;
import com.example.administrator.readingtwtnews2.adapters.SpacesItemDecoration;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


/**
 * Created by Administrator on 2017/6/18.
 */

public class HistoryRecordFragment extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private View linearLayout;
    private Intent mIntent;
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.history_record);
        mRecyclerView = (RecyclerView) findViewById(R.id.recordRecyler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new RecyclerAdapter(this);
        mAdapter.setCALL_ACTIVITY(RecyclerAdapter.CALL_RECORD);
        Toolbar toolbar = (Toolbar)findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(v -> {finish();});
        mAdapter.setOnItemClickListener(((position, o, v) -> {
            mIntent = new Intent();
            NewsBean.DataBean newsItem = (NewsBean.DataBean)o;
            mIntent.setClass(HistoryRecordFragment.this, ReadNews.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("object", newsItem);
            mIntent.putExtras(bundle);
            startActivity(mIntent);
        }));

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration decor = new SpacesItemDecoration(25);
        mRecyclerView.addItemDecoration(decor, -1);
        DataBaseHelper mHelper = new DataBaseHelper();
        getData(mHelper);
    }

    public void getData(DataBaseHelper mHelper) {
        Observable<List<NewsBean.DataBean>> observable = Observable.just(mHelper)
                .map(dataBaseHelper -> dataBaseHelper.queryRecord())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        Subscriber<List<NewsBean.DataBean>> subscriber = new Subscriber<List<NewsBean.DataBean>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(List<NewsBean.DataBean> dataBeen) {
                mAdapter.addAll(dataBeen);
            }
        };
        observable.subscribe(subscriber);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.history_record_menu, menu);
        return true;
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        Log.i("菜单","点击");
        mAdapter.clear();
        new DataBaseHelper().deleteAllRecord();
        return false;
    }
}

package com.example.administrator.readingtwtnews2.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

public class LoveFragment extends Fragment {
    private View linearLayout;
    private Intent mIntent;
    private RecyclerAdapter mAdapter;
    private RecyclerView mRecyclerView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getActivity();
        Log.i("HISTORY","DIAOYONG");
        linearLayout = inflater.inflate(R.layout.love_fragment,null);
        Toolbar toolbar = new Toolbar(context);
        toolbar.setTitle("我的收藏");
        ((AppCompatActivity)context).setSupportActionBar(toolbar);
        mRecyclerView = (RecyclerView)linearLayout.findViewById(R.id.recordRecyler);


        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));



        mAdapter = new RecyclerAdapter(getActivity());
        mAdapter.setCALL_ACTIVITY(RecyclerAdapter.CALL_RECORD);
        mAdapter.setOnItemClickListener(new OnItemClickListener<NewsBean.DataBean>() {
            @Override
            public void onItemClick(int position, NewsBean.DataBean newsItem, View v) {
                mIntent = new Intent();
                mIntent.setClass(getActivity(), ReadNews.class);
                mIntent.putExtra("url", newsItem.getIndex());
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", newsItem);
                mIntent.putExtras(bundle);

                startActivity(mIntent);

            }
        });

        mRecyclerView.setAdapter(mAdapter);
        RecyclerView.ItemDecoration decor = new SpacesItemDecoration(25);
        mRecyclerView.addItemDecoration(decor,-1);
        return linearLayout;

    }
    @Override
    public void onResume(){
        super.onResume();
        mAdapter.clear();
        getData(new DataBaseHelper());
    }

    public void getData(DataBaseHelper mHelper) {
        Observable<List<NewsBean.DataBean>> observable = Observable.just(mHelper)
                .map(dataBaseHelper -> dataBaseHelper.queryLove())
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
}

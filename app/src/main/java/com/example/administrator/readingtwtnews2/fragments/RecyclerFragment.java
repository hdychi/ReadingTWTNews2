package com.example.administrator.readingtwtnews2.fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.administrator.readingtwtnews2.DataBaseHelper;
import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.OnItemClickListener;
import com.example.administrator.readingtwtnews2.R;
import com.example.administrator.readingtwtnews2.ReadNews;
import com.example.administrator.readingtwtnews2.adapters.RecyclerAdapter;
import com.example.administrator.readingtwtnews2.adapters.SpacesItemDecoration;
import com.example.administrator.readingtwtnews2.api.APIClient;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

import rx.Notification;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by asus on 2016/10/23.
 */
public class RecyclerFragment extends Fragment {
    private View linearLayout;
    private Intent mIntent;

    private RecyclerAdapter mAdapter;
    private int kind = 0;
    private int page;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private boolean isLoaing;
    private APIClient client;
    private Subscriber<NewsBean> subscriber;
    private int state;
    private DataBaseHelper mHelper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Context context = getContext();
        mHelper = new DataBaseHelper();
        mHelper.createTable();
        linearLayout = inflater.inflate(R.layout.recycler_fragment_layout, null);
        swipeRefreshLayout = (SwipeRefreshLayout) linearLayout.findViewById(R.id.swipe);
        mRecyclerView = (RecyclerView) linearLayout.findViewById(R.id.recyler);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        page = 1;
        swipeRefreshLayout.setOnRefreshListener(() -> {
            myonRefresh();
        });
        mAdapter = new RecyclerAdapter(context);
        mAdapter.setOnItemClickListener(new OnItemClickListener<NewsBean.DataBean>() {
            @Override
            public void onItemClick(int position, NewsBean.DataBean newsItem, View v) {
                mIntent = new Intent();
                mIntent.setClass(getActivity(), ReadNews.class);
                mIntent.putExtra("url", newsItem.getIndex());
                Bundle bundle = new Bundle();
                bundle.putSerializable("object", newsItem);
                mIntent.putExtras(bundle);
                writeItem(newsItem);
                startActivity(mIntent);

            }
        });
        mAdapter.setCALL_ACTIVITY(RecyclerAdapter.CALL_NEWS);

        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                LinearLayoutManager manageer = (LinearLayoutManager) mRecyclerView.getLayoutManager();
                int totalItemCount = manageer.getItemCount();
                int lastVisibleItem = manageer.findLastVisibleItemPosition();
                if (!isLoaing && totalItemCount < lastVisibleItem + 3) {
                    onLoadMore();
                    mAdapter.setLoadState(0);
                }
            }
        });

        RecyclerView.ItemDecoration decor = new SpacesItemDecoration(25);
        mRecyclerView.addItemDecoration(decor, -1);
        client = new APIClient();

        return linearLayout;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        isLoaing = false;

        if (getArguments() != null) {
            kind = (int) getArguments().get("newsType");

        }
        if (mAdapter.getItemCount() == 1 && kind > 0) {
            loadItems(1);
            isLoaing = true;
        }
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        linearLayout = null;
        mAdapter.clear();
        super.onDestroyView();
    }

    public void loadItems(int temp) {

        isLoaing = true;
        mAdapter.setLoadState(RecyclerAdapter.IS_LOADING);
        state = temp;
        subscriber = new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                dialog(1);

            }

            @Override
            public void onNext(NewsBean newsBean) {
                Log.i("加载更多","返回2");
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                isLoaing = false;
                List<NewsBean.DataBean> items = newsBean.getData();
                if (items != null) {
                    Log.i("返回数据","不为空");
                    for (int i = 0; i < items.size(); i++) {
                        NewsBean.DataBean item = items.get(i);
                        if(!mAdapter.contains(item)){
                            mAdapter.add(item);
                        }
                    }
                }
                else{
                    Log.i("返回数据","为空");
                }
            }
        };
        client.getNews(kind, page, subscriber);

    }


    public void myonRefresh() {
        state = 0;
        subscriber = new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                swipeRefreshLayout.setRefreshing(false);
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                dialog(0);

            }

            @Override
            public void onNext(NewsBean newsBean) {
                Log.i("加载更多","返回2");
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                isLoaing = false;
                swipeRefreshLayout.setRefreshing(false);
                List<NewsBean.DataBean> items = newsBean.getData();
                if (items != null) {
                    for (int i = 0; i < items.size(); i++) {
                        NewsBean.DataBean item = items.get(i);
                        if(!mAdapter.contains(item)){
                            mAdapter.addFront(item);
                        }
                        else{
                            Log.i("去重","有用");
                        }
                    }
                }

            }
        };
        client.getNews(kind, 1, subscriber);
    }

    public void onLoadMore() {
        Log.i("加载更多","调用");
        isLoaing = true;
        page++;
        mAdapter.setLoadState(RecyclerAdapter.IS_LOADING);
        subscriber = new Subscriber<NewsBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                dialog(1);

            }

            @Override
            public void onNext(NewsBean newsBean) {
                Log.i("加载更多","返回2");
                mAdapter.setLoadState(RecyclerAdapter.NOT_LOADING);
                isLoaing = false;
                List<NewsBean.DataBean> items = newsBean.getData();
                if (items != null) {
                    Log.i("返回数据","不为空");
                    for (int i = 0; i < items.size(); i++) {
                        NewsBean.DataBean item = items.get(i);
                        if(!mAdapter.contains(item)){
                            mAdapter.add(item);
                        }
                    }
                }
                else{
                    Log.i("返回数据","为空");
                }
            }
        };
        client.getNews(kind, page, subscriber);

    }

    public void writeItem(NewsBean.DataBean item) {

        Observable<Boolean> observable = Observable.just(item)
                .map(dataBean -> mHelper.writeRecord(dataBean))

                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io());
        observable.subscribe(new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                e.printStackTrace();
            }

            @Override
            public void onNext(Boolean aBoolean) {
               Log.i("记录","写入成功");
            }
        });


    }

    public void dialog(int state) {
        final int temp = state;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("网络连接超时，是否刷新？");
        builder.setTitle("提示");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                loadItems(temp);
                dialog.dismiss();

            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (temp == 1) {

                } else {

                }
                dialog.dismiss();
            }


        });

        builder.create().show();
    }

}

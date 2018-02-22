package com.example.administrator.readingtwtnews2.fragments;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.administrator.readingtwtnews2.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/18.
 */

public class MainFragment extends Fragment{
    private List<Fragment> fragmentList;
    private List<String> tabs;

    private fragmentAdapter mFragmentAdapter;
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private LinearLayout relativeLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){
        relativeLayout =(LinearLayout) inflater.inflate(R.layout.main_fragment,null);
        Log.i("mainFragment","diaoyong");
        tabs = new ArrayList<String>();
        tabs.add("天大要闻");

        tabs.add("校园公告");
        tabs.add("社团风采");
        tabs.add("院系动态");
        tabs.add("视点观察");
        initFragment();
        initTab();
        return relativeLayout;

    }
    @Override
    public void onDestroyView(){
        relativeLayout = null;
        tabs.clear();
        fragmentList.clear();
        super.onDestroyView();
    }
    public  void initFragment(){
        fragmentList = new ArrayList<Fragment>();
        for(int i = 0;i<5;i++){
            RecyclerFragment mFragment = new RecyclerFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("newsType",i+1);
            mFragment.setArguments(bundle);
            fragmentList.add(mFragment);
        }
        FragmentManager fm =   getChildFragmentManager();
        mFragmentAdapter = new fragmentAdapter(fm,fragmentList);

    }
    public  void initTab(){
        //  mTabLayout = (TabLayout)findViewById(R.id.tablayout);
        //  mViewPager = (ViewPager)findViewById(R.id.viewPager);
        mTabLayout =(TabLayout)relativeLayout.findViewById(R.id.tablayout);
        mViewPager = (ViewPager)relativeLayout.findViewById(R.id.viewPager);


        mViewPager.setAdapter(mFragmentAdapter);
        mViewPager.setCurrentItem(0);
        mTabLayout.setTabTextColors(getResources().getColorStateList(R.color.black));
        mTabLayout.setupWithViewPager(mViewPager);
        for(int i = 0;i < 5;i++){

            mTabLayout.getTabAt(i).setText(tabs.get(i));

        }




    }
}

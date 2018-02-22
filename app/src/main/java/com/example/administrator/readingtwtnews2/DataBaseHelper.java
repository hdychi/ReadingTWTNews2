package com.example.administrator.readingtwtnews2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.util.Log;

import com.example.administrator.readingtwtnews2.Models.NewsBean;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/17.
 */

public class DataBaseHelper {
    private SQLiteDatabase mDatabase;

    public DataBaseHelper() {
        File file = new File(Environment.getExternalStorageDirectory() + "/readingnews");
        if (!file.exists()) {
            try {
                if (!file.mkdirs()) {
                    System.out.println("创建失败");
                }

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("创建文件异常");
            }
        }
        String databasePath = Environment.getExternalStorageDirectory() + "/readingnews/" + "data.db";
        mDatabase = SQLiteDatabase.openOrCreateDatabase(databasePath, null);
        Cursor cursor = mDatabase.rawQuery("select name from sqlite_master where type='table' order by name", null);
        while (cursor.moveToNext()) {
            //遍历出表名
            String name = cursor.getString(0);
            Log.i("表名遍历", name);
        }


    }

    public void createTable() {
        String str1 = "create table if not exists Record(mindex integer primary key,msubject varchar(100),pic varchar(100),summary varchar(100));";
        String str2 = "create table if not exists Love(mindex integer primary key,msubject varchar(100),pic varchar(100),summary varchar(100));";
        mDatabase.execSQL(str1);
        mDatabase.execSQL(str2);
    }

    public boolean writeRecord(NewsBean.DataBean news) {
        mDatabase.execSQL("insert into Record(mindex,msubject,pic,summary) values(?,?,?,?)",
                new Object[]{news.getIndex(), news.getSubject(), news.getPic(), news.getSummary()});
        return true;
    }
    public boolean writeLove(NewsBean.DataBean news){
        mDatabase.execSQL("insert into Love(mindex ,msubject ,pic,summary) values(?,?,?,?)",
                new Object[]{news.getIndex(), news.getSubject(), news.getPic(), news.getSummary()});
        return true;
    }
    public List<NewsBean.DataBean> queryRecord(){
        List<NewsBean.DataBean> res = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from Record",null);
        if(cursor!=null&&cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                NewsBean.DataBean item = new NewsBean.DataBean();
                item.setIndex(cursor.getInt(0));
                item.setSubject(cursor.getString(1));
                item.setPic(cursor.getString(2));
                item.setSummary(cursor.getString(3));
                res.add(item);
                cursor.moveToNext();
            }
        }
        return res;
    }
    public List<NewsBean.DataBean> queryLove(){
        List<NewsBean.DataBean> res = new ArrayList<>();
        Cursor cursor = mDatabase.rawQuery("select * from Love",null);
        if(cursor!=null&&cursor.moveToFirst()) {

            while (!cursor.isAfterLast()) {
                NewsBean.DataBean item = new NewsBean.DataBean();
                item.setIndex(cursor.getInt(0));
                item.setSubject(cursor.getString(1));
                item.setPic(cursor.getString(2));
                item.setSummary(cursor.getString(3));
                res.add(item);
                cursor.moveToNext();
            }
        }
        return res;
    }
    public boolean deleteLove(int index){
        mDatabase.delete("Love","mindex =?",new String[]{index+""});
        return true;
    }
    public void deleteAllRecord(){
        mDatabase.delete("Record",null,null);
    }
}

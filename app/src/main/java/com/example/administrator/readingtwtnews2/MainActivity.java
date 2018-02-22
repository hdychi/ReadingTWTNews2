package com.example.administrator.readingtwtnews2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.administrator.readingtwtnews2.Models.NewsBean;
import com.example.administrator.readingtwtnews2.api.APIClient;
import com.example.administrator.readingtwtnews2.fragments.HistoryRecordFragment;
import com.example.administrator.readingtwtnews2.fragments.LoveFragment;
import com.example.administrator.readingtwtnews2.fragments.MainFragment;
import com.github.piasy.biv.BigImageViewer;
import com.github.piasy.biv.loader.fresco.FrescoImageLoader;

import java.util.List;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static int PERMISSION_REQUSET_CODE = 1;
    private Toolbar toolbar;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setStatusBarColor(getResources().getColor(R.color.lightBlue));
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        navigationView = (NavigationView) findViewById(R.id.nav);
        navigationView.setNavigationItemSelectedListener(this);
        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment fragment = new MainFragment();
        fragmentManager.beginTransaction().replace(R.id.framLayout, fragment).commit();

        Drawable drawable = getResources().getDrawable(R.drawable.menu_icon);
        toolbar.setNavigationIcon(drawable);
        toolbar.setTitle("天外天新闻阅读");
        toolbar.setElevation(0);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(v -> {
            Log.i("监听","调用");
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
            drawer.openDrawer(GravityCompat.START);
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        dealWithPermissions();
        BigImageViewer.initialize(FrescoImageLoader.with(getApplicationContext()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return true;
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        switch (id) {
            case R.id.nav_main:
                Fragment fragment = new MainFragment();
                fragmentManager.beginTransaction().replace(R.id.framLayout, fragment).commit();
                toolbar.setTitle("天外天新闻阅读");
                break;
            case R.id.nav_record:
                Intent intent = new Intent();
                intent.setClass(MainActivity.this, HistoryRecordFragment.class);
                startActivity(intent);
                break;
            case R.id.nav_collect:
                Fragment fragment2 = new LoveFragment();
                fragmentManager.beginTransaction().replace(R.id.framLayout, fragment2).commit();
                toolbar.setTitle("我的收藏");

                break;
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer);
        drawer.closeDrawer(GravityCompat.START);

        return true;
    }

    public void dealWithPermissions() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_REQUSET_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUSET_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

}

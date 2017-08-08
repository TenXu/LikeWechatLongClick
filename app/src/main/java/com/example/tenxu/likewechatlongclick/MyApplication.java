package com.example.tenxu.likewechatlongclick;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Handler;

import java.util.LinkedList;
import java.util.List;

/**
 * 全局Application
 */
public class MyApplication extends Application {

    public static List<Activity> activities = new LinkedList<>();

    private static Context mContext;//上下文

    private static Handler mHandler;//主线程Handler

    private static long mMainThreadId;//主线程id

//    public static final boolean ENCRYPTED = true;//数据库是否加密

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();

        mHandler = new Handler();
        mMainThreadId = android.os.Process.myTid();

    }

    public static Context getContext() {
        return mContext;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
    }

    public static void setContext(Context context) {
        MyApplication.mContext = context;
    }

    public static Context getmContext() {
        return mContext;
    }

    public static long getMainThreadId() {
        return mMainThreadId;
    }

    public static void setMainThreadId(long mMainThreadId) {
        MyApplication.mMainThreadId = mMainThreadId;
    }

    public static void setMainHandler(Handler mHandler) {
        MyApplication.mHandler = mHandler;
    }

    public static Handler getMainHandler() {
        return mHandler;
    }
}

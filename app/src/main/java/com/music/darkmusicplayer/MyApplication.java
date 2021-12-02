package com.music.darkmusicplayer;

import android.app.Application;
import android.content.Context;

public class MyApplication extends Application {

    private RxBus bus;
    private static Context  mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        bus = new RxBus();
        mContext=getApplicationContext();
    }

    public static Context getAppContext() {
        return mContext;
    }
    public RxBus bus() {
        return bus;
    }
}

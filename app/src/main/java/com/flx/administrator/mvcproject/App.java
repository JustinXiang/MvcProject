package com.flx.administrator.mvcproject;

import android.app.Application;


/**
 * Created by Administrator.
 */

public class App extends Application {
    public static App mInstance;
    public static App getInstance(){
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance =this;
        init();
    }

    private void init() {

    }
}

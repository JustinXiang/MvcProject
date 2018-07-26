package com.flx.administrator.mvcproject.manager;

import android.content.Context;
import android.content.SharedPreferences;

import com.flx.administrator.mvcproject.App;


/**
 * Created by Administrator.
 */

public class Sp {

    private static final String IS_FIRST = "IS_FIRST";
    private static final String IS_LOGIN = "IS_LOGIN";

    public static SharedPreferences getInstance(){
        return App.getInstance().getSharedPreferences("AppSp", Context.MODE_PRIVATE);
    }
    public static void setIsFirst(boolean isFirst){
        getInstance().edit().putBoolean(IS_FIRST,isFirst).commit();
    }
    public static boolean getIsFirst(){
        return getInstance().getBoolean(IS_FIRST ,true);
    }
    /**
     * 设置是否登陆过
     * @param isLogin true：登陆过  false:没登陆过
     */
    public static void setLoginStatus(boolean isLogin){
        getInstance().edit().putBoolean(IS_LOGIN,isLogin).commit();
    }

    /**
     * 获取用户是否登陆过
     */
    public static boolean getLoginStatus(){
        return getInstance().getBoolean(IS_LOGIN ,false);
    }


}

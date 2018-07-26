package com.flx.administrator.mvcproject.utils;

import android.content.Context;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.flx.administrator.mvcproject.App;

/**
 * Created by lauyk on 2016/9/28.
 * Toast 工具类
 */

public class ToastUtils {

    private static View inflate;
    private static TextView tv_toast;
    private static Toast toast;

    /**
     * 短时间显示Toast
     *
     * @param mContext
     * @param message
     */
    public static void showShorts(Context mContext, CharSequence message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }



    public static void showShort(Context mContext, String str) {
        toast(mContext,str);
    }



    /**
     * 短时间显示Toast
     *
     * @param mContext
     * @param message
     */
    public static void showShort(Context mContext, int message) {
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param mContext
     * @param message
     */
    public static void showLong(Context mContext, CharSequence message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 长时间显示Toast
     *
     * @param mContext
     * @param message
     */
    public static void showLong(Context mContext, int message) {
        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param mContext
     * @param message
     * @param duration
     */
    public static void show(Context mContext, CharSequence message, int duration) {
        Toast.makeText(mContext, message, duration).show();
    }

    /**
     * 自定义显示Toast时间
     *
     * @param mContext
     * @param message
     * @param duration
     */
    public static void show(Context mContext, int message, int duration) {
        Toast.makeText(mContext, message, duration).show();
    }

    private static void toast(Context mContext, String str) {
        if (str == null)
            str = "";
        if (null != toast)
            toast.cancel();
        toast = Toast.makeText(mContext, str, Toast.LENGTH_SHORT);
        toast.setDuration(Toast.LENGTH_SHORT);
        toast.show();
    }

    public static void showToast(String content){
        Toast.makeText(App.getInstance(),content,Toast.LENGTH_SHORT);
    }


}

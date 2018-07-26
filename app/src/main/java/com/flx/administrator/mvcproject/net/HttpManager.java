package com.flx.administrator.mvcproject.net;


import android.content.Context;
import android.os.Build;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.flx.administrator.mvcproject.BuildConfig;
import com.flx.administrator.mvcproject.R;
import com.flx.administrator.mvcproject.base.BaseActivity;
import com.flx.administrator.mvcproject.base.BaseFragment;
import com.flx.administrator.mvcproject.manager.Sp;
import com.flx.administrator.mvcproject.net.api.CallBack;
import com.flx.administrator.mvcproject.utils.ToastUtils;
import com.trello.rxlifecycle2.android.ActivityEvent;
import com.trello.rxlifecycle2.android.FragmentEvent;
import com.trello.rxlifecycle2.components.support.RxDialogFragment;

import java.util.Map;
import java.util.Set;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * Created by Administrator.
 */

public class HttpManager {
    /**
     * Parameters公共参数，自己修改
     * @param values
     * @param context
     * @return
     */
    public static String getParameters(Map<String, String> values, Context context){
        StringBuffer jsonStr = new StringBuffer();
        jsonStr.append("{");

        if (values!=null && !values.isEmpty()){
            Set<?> keySet = values.keySet();
            for (Object key : keySet) {
                jsonStr.append("\"" + key + "\":\"" + values.get(key) + "\",");
            }
        }
        // "\"authorization\":\"" + SP.getAuthorization(context))+

        jsonStr.append(
                "\",\"phoneSys\":\"" + Build.MANUFACTURER + " " + Build.MODEL +
                "\",\"sysVersion\":\"" + Build.BRAND + " " + Build.VERSION.RELEASE +
                "\",\"APPVersion\":\"" + BuildConfig.VERSION_NAME +
                "\",\"verNum\":\""+"115"+
                "\",\"appPlatform\":\"1\",\"channel\":\""  +
                "\",\"appPackageName\":\"" + context.getString(R.string.app_name) + "\"}");
        String JSONSTR=jsonStr.toString().replaceAll("\\r\\n","");//去掉所有的换行符
        return JSONSTR;
    }


    public static void post4Activity(Observable<String> observable, final BaseActivity act, final int tag, final CallBack callBack){

                observable
                .compose(RxUtil.<String>rxSchedulerHelper())
                .compose(act.<String>bindUntilEvent(ActivityEvent.DESTROY))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
//                        act.show();
                        JSONObject jsonObject = JSON.parseObject(value);
                        String end = jsonObject.getString("end");
                        if("ok".equals(end)){
                            callBack.OnSuccess(jsonObject,tag);
                        }else if("noLogin".equals(end)){
                            ToastUtils.showToast("你的账号在其它设备上登陆");
                            Sp.setLoginStatus(false);
                            //跳转登录页面
                        }else if ("noData".equals(end)){
                            ToastUtils.showToast("没有数据");
                        }else {
                            String msg = jsonObject.getString("message");
                            callBack.OnFail(msg,tag);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void post4Fragment(Observable<String> observable, final BaseFragment frg, final int tag, final CallBack callBack){

                observable
                .compose(RxUtil.<String>rxSchedulerHelper())
                .compose(frg.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
//                        frg.show();
                        JSONObject jsonObject = JSON.parseObject(value);
                        String end = jsonObject.getString("end");
                        if("ok".equals(end)){
                            callBack.OnSuccess(jsonObject,tag);
                        }else if("noLogin".equals(end)){
                            ToastUtils.showToast("你的账号在其它设备上登陆");
                            Sp.setLoginStatus(false);
                            //跳转登录页面
                        }else if ("noData".equals(end)){
                            ToastUtils.showToast("没有数据");
                        }else {
                            String msg = jsonObject.getString("message");
                            callBack.OnFail(msg,tag);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public static void post4Dialog(Observable<String> observable, final RxDialogFragment frg, final int tag, final CallBack callBack){

        observable
                .compose(RxUtil.<String>rxSchedulerHelper())
//                .compose(frg.<String>bindUntilEvent(FragmentEvent.DESTROY))
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(String value) {
                        JSONObject jsonObject = JSON.parseObject(value);
                        String end = jsonObject.getString("end");
                        if("ok".equals(end)){
                            callBack.OnSuccess(jsonObject,tag);
                        }else if("noLogin".equals(end)){
                            ToastUtils.showToast("你的账号在其它设备上登陆");
                            Sp.setLoginStatus(false);
                            //跳转登录页面
                        }else if ("noData".equals(end)){
                            ToastUtils.showToast("没有数据");
                        }else {
                            String msg = jsonObject.getString("message");
                            callBack.OnFail(msg,tag);
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}

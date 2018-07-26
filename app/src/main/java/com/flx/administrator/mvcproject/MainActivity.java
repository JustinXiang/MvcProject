package com.flx.administrator.mvcproject;

import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.flx.administrator.mvcproject.base.BaseActivity;
import com.flx.administrator.mvcproject.bean.XfStoryBean;
import com.flx.administrator.mvcproject.manager.TitleManager;
import com.flx.administrator.mvcproject.net.HttpManager;
import com.flx.administrator.mvcproject.net.RetrofitHelper;
import com.flx.administrator.mvcproject.net.api.ApiParam;
import com.flx.administrator.mvcproject.net.api.CallBack;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;


public class MainActivity extends BaseActivity implements CallBack {
    private TextView listview;
    private List<XfStoryBean> xfStoryBeans;

    @Override
    protected TitleManager addTitle(TitleManager manager) {
        return null;
    }

    @Override
    protected int getContentId() {
        return R.layout.activity_main;
    }

    @Override
    protected void onLogicCreate(View view) {
        listview = $(R.id.listview);
        getData();
    }

    private void getData() {
        Observable<String> observable = RetrofitHelper.getApiService()
                .getStoryList(ApiParam.getStoryList(this));
        HttpManager.post4Activity(observable,this,1,this);
    }

    @Override
    public void OnSuccess(JSONObject data, int tag) {
        switch (tag){
            case 1:
                JSONObject data1 = data.getJSONObject("data");
                JSONArray xfStorylist = data1.getJSONArray("xfStorylist");
                xfStoryBeans = JSON.parseArray(xfStorylist.toString(), XfStoryBean.class);
                listview.setText(xfStoryBeans.get(0).title);
                break;
                }
    }

    @Override
    public void OnFail(String msg, int tag) {

    }
}

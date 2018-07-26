package com.flx.administrator.mvcproject.net.api;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by Administrator.
 */

public interface CallBack {
    void OnSuccess(JSONObject data, int tag);
    void OnFail(String msg, int tag);
}

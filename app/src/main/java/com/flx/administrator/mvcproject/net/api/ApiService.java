package com.flx.administrator.mvcproject.net.api;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by Administrator.
 */

public interface ApiService {

    @FormUrlEncoded
    @POST("api/XFStory/getStoryList")
    Observable<String> getStoryList(@FieldMap Map<String, String> map);
}

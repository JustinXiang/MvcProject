package com.flx.administrator.mvcproject.net;

import android.util.Log;

import com.flx.administrator.mvcproject.App;
import com.flx.administrator.mvcproject.BuildConfig;
import com.flx.administrator.mvcproject.net.api.ApiService;
import com.flx.administrator.mvcproject.net.api.ToStringConverterFactory;
import com.flx.administrator.mvcproject.utils.NetUtil;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

/**
 * Created by Administrator.
 */

public class RetrofitHelper {

    public static final String BASE_URL = BuildConfig.SERVER_HOST;

    static {
        initOkHttpClient();
    }

    private static OkHttpClient okHttpClient;

    public static ApiService getApiService(){
        return createApi(ApiService.class, BASE_URL);
    }

    private static <T> T createApi(Class<T> clazz, String baseUrl){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(new ToStringConverterFactory())
                .client(okHttpClient)
                .build();
        return retrofit.create(clazz);
    }

    private static  void initOkHttpClient(){
        if(okHttpClient == null){
            synchronized (RetrofitHelper.class){
                HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {
                        if(BuildConfig.LOG_DEBUG) {
                            Log.d("okhttp", message);
                        }
                    }
                });
                httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
                Cache cache = new Cache(new File(App.getInstance().getCacheDir(), "HttpCache"), 1024 * 1024 * 10);
                okHttpClient = new OkHttpClient.Builder()
                        .cache(cache)
                        .addNetworkInterceptor(httpLoggingInterceptor)
                        .addInterceptor(new CacheInterceptor())
                        .connectTimeout(30, TimeUnit.SECONDS)
                        .writeTimeout(20, TimeUnit.SECONDS)
                        .readTimeout(20, TimeUnit.SECONDS)
                        .build();
            }
        }
    }
    /**
     * 为okhttp添加缓存，这里是考虑到服务器不支持缓存时，从而让okhttp支持缓存
     */
    private static class CacheInterceptor implements Interceptor {
        @Override
        public Response intercept(Chain chain) throws IOException {
            // 有网络时 设置缓存超时时间1个小时
            int maxAge = 60 * 60;
            // 无网络时，设置超时为1天
            int maxStale = 60 * 60 * 24;
            Request request = chain.request();
            if (NetUtil.isNetworkAvailable(App.getInstance())) {
                //有网络时只从网络获取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_NETWORK).build();
            } else {
                //无网络时只从缓存中读取
                request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
            }
            Response response = chain.proceed(request);
            if (NetUtil.isNetworkAvailable(App.getInstance())) {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                response = response.newBuilder()
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build();
            }
            return response;
        }
    }
}

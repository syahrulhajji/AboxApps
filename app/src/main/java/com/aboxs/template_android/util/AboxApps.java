package com.aboxs.template_android.util;

import android.support.multidex.MultiDexApplication;

import com.aboxs.template_android.BuildConfig;
import com.aboxs.template_android.api.AboxApi;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Abox's on 10/02/2018.
 */

public class AboxApps extends MultiDexApplication {
    @Override
    public void onCreate() {
        super.onCreate();

    }
    public static AboxApi getAPI() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(AboxApi.SERVER).client(getClient())
                .addConverterFactory(GsonConverterFactory.create()).build();
        return retrofit.create(AboxApi.class);
    }
    public static OkHttpClient getClient() {

        return new OkHttpClient.Builder()
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(1,TimeUnit.MINUTES)
                .writeTimeout(1,TimeUnit.MINUTES)
                .addInterceptor(getLoggingInterceptor())
                .build();
    }

    public static HttpLoggingInterceptor.Level getInterceptorLevel() {
        if (BuildConfig.DEBUG) return HttpLoggingInterceptor.Level.BODY;
        else return HttpLoggingInterceptor.Level.NONE;
    }

    public static HttpLoggingInterceptor getLoggingInterceptor() {
        HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        httpLoggingInterceptor.setLevel(getInterceptorLevel());
        return httpLoggingInterceptor;
    }
}

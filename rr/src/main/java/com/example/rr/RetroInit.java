package com.example.rr;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetroInit {

    private static RetroInit RETROINT;
    private static final int DEFAULT_TIMEOUT = 10000;

    public static RetroInit getDefault() {

        if (RETROINT == null) {
            OkHttpClient.Builder builder = new OkHttpClient.Builder();
            builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

            builder.addInterceptor(new Interceptor() {
                @Override
                public Response intercept(Chain chain) throws IOException {
                    Request request = chain.request();
                    Response proceed = chain.proceed(request);
                    return proceed.newBuilder().header("key1", "value1").addHeader("key2", "value2").build();
                }
            });
            RETROINT = new Retrofit.Builder()
                    .client(builder.build())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(Url.BASE_URL)
                    .build().create(RetroInit.class);
        }
        return RETROINT;
    }
}
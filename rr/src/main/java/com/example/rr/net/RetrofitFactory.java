package com.example.rr.net;

import com.example.rr.api.ApiService;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    private RetrofitFactory() {
        new RuntimeException("正在写");

    }

    private static OkHttpClient httpClient = GysOkHttpClient.getInstance();
    private static ApiService retrofitService;
    private static String baseUrl = "";
    private static Retrofit retrofit;

    /**
     * baseUrl
     */
    private static void getBaseUrl() {
        baseUrl = HttpConfig.getServer();
    }

    private static Retrofit getInstanceRetrofit() {
        if (retrofit == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofit == null) {
                    if (baseUrl != null) {
                        getBaseUrl();
                    }
                    retrofit = new Retrofit.Builder()
                            .baseUrl(baseUrl)
                            .addConverterFactory(GsonConverterFactory.create())
                            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                            .client(httpClient)
                            .build();
                }
            }
        }
        return retrofit;
    }

    /**
     * 默认的ApiService
     *
     * @return
     */
    public static ApiService getInstance() {
        if (retrofitService == null) {
            synchronized (RetrofitFactory.class) {
                if (retrofitService == null) {
                    retrofitService = getInstanceRetrofit().create(ApiService.class);
                }
            }
        }
        return retrofitService;
    }

    /**
     * 用于创建自定义的apiService
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T createRetrofitService(Class<T> tClass) {
        return getInstanceRetrofit().create(tClass);
    }
}

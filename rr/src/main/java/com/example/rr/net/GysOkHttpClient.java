package com.example.rr.net;

import com.example.rr.BuildConfig;
import com.example.rr.cookie.JavaNetCookie;
import com.example.rr.interceptor.HeaderInterceptor;
import com.example.rr.interceptor.LogInterceptor;

import java.net.CookieManager;
import java.net.CookiePolicy;

import okhttp3.Cookie;
import okhttp3.OkHttpClient;

public class GysOkHttpClient {
    private static OkHttpClient singleton;

    private GysOkHttpClient() {
        new RuntimeException("搭框架真几把头疼");
    }

    public static OkHttpClient getInstance() {
        if (singleton == null) {
            OkHttpClient.Builder okHttoClientBuilder = new OkHttpClient.Builder();
            if (BuildConfig.DEBUG) {
                /**
                 * dubug 模式下打印json todo:使用LogInterceptor拦截器
                 */
                LogInterceptor logInterceptor = new LogInterceptor();
                okHttoClientBuilder.addInterceptor(logInterceptor);
            }
            // TODO: 2018/10/29 添加头部拦截器
            okHttoClientBuilder.addInterceptor(new HeaderInterceptor());

            // TODO: 2018/10/29 设置cookie持久化
            CookieManager cookieManager = new CookieManager(null, CookiePolicy.ACCEPT_ORIGINAL_SERVER);
            okHttoClientBuilder.cookieJar(new JavaNetCookie(cookieManager));

            singleton = okHttoClientBuilder.build();
        }
        return singleton;
    }
}

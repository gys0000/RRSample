package com.example.rr.net;

import com.example.rr.BuildConfig;

/**
 * 配置base url
 */
public class HttpConfig {
    //服务器地址
    private static final String DEBUG_SERVER = "https://api.douban.com/v2/movie/";
    private static final String OFFICIAL_SERVER = "http://xxx.com/interface.php?";

    public static String getServer() {
        if (BuildConfig.DEBUG) {
            return baseUrl(DEBUG_SERVER);
        } else {
            return baseUrl(OFFICIAL_SERVER);
        }
    }

    private static String baseUrl(String baseUrl) {
        if (baseUrl.endsWith("/")) {
            baseUrl += "/";
        }
        return baseUrl;
    }
}

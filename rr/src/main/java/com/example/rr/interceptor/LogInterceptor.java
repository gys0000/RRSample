package com.example.rr.interceptor;

import android.net.Uri;
import android.util.Log;

import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Locale;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * 打印网络请求时传输的字段还有返回的json数据
 */
public class LogInterceptor implements Interceptor {

    private final static String TAG = LogInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Response response = chain.proceed(chain.request());
        long t2 = System.nanoTime();

        StringBuffer sb = new StringBuffer();
        sb.append(request.method()).append("\n");
        String[] url = request.url().toString().split("\\?");
        sb.append(url[0]).append("\n");
        if (url.length == 2) {
            String[] params = url[1].split("&");
            for (String param : params) {
                sb.append(Uri.decode(param)).append("\n");
            }
        }

        if (request.body() instanceof FormBody) {
            FormBody postParams = (FormBody) request.body();
            if (postParams != null) {
                sb.append("post:").append("\n");
                int size = postParams.size();
                for (int i = 0; i < size; i++) {
                    sb.append(postParams.encodedName(i) + "=" + URLDecoder.decode(postParams.encodedValue(i), "utf-8")).append("\n");
                }
            }
        }

        MediaType mediaType = response.body().contentType();
        String content = response.body().string();
        /**
         * 格式化打印json
         */
        Log.e(TAG, String.format(Locale.getDefault(), "%s cost %.1fms%n%s", sb.toString(), (t2 - t1) / 1e6d, format(content)));

        return response.newBuilder()
                .body(ResponseBody.create(mediaType, content))
                .build();
    }

    private String format(String content) {

        int level = 0;
        StringBuffer jsonForMatStr = new StringBuffer();
        for (int i = 0; i < content.length(); i++) {
            char c = content.charAt(i);
            if (level > 0 && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < level; i++) {
            stringBuffer.append("\t");
        }
        return stringBuffer.toString();
    }
}

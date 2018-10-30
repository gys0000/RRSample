package com.example.rr.net;

import android.net.Uri;

import com.example.rr.base.BaseObserver;
import com.example.rr.cache.CacheObject;
import com.example.rr.cache.CacherManager;
import com.example.rr.interfaces.IResponse;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

/**
 * 请求的封装入口
 */
public class RequestUtil {
    /**
     * get方式处理
     *
     * @param url
     * @param map
     * @param iResponse
     * @param <T>
     */
//    public static <T> Observable<ResponseBody> getDispose(String url, Map map, final IResponse<T> iResponse) {
//        Observable<ResponseBody> observable = RetrofitFactory.getInstance().executeGet(url, map);
//        return getObservable(observable, iResponse, null);
//    }
//
//    private static <T> Observable<ResponseBody> getDispose(String url, Map map, IResponse<T> iResponse, Map cacheMap) {
//        Observable<ResponseBody> observable = RetrofitFactory.getInstance().executeGet(url, map);
//        return getObservable(observable, iResponse, cacheMap);
//    }

    /**************************************post**************************************/

//    public static <T> void postDispose(String url, Map map, final IResponse<T> iResponse) {
//
//        Observable<ResponseBody> observable = RetrofitFactory.getInstance().executePost(url, map);
//        observable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new BaseObserver<>(iResponse));
//    }
//
//    private static <T> Observable<ResponseBody> postDispose(String url, Map map, final IResponse<T> iResponse, Map cacheMap) {
//        Observable<ResponseBody> observable = RetrofitFactory.getInstance().executePost(url, map);
//        return getObservable(observable, iResponse, cacheMap);
//    }

    /**
     * 自定义的AppiService
     *
     * @param tClass
     * @param <T>
     * @return
     */
    public static <T> T getCustomService(Class<T> tClass) {
        return RetrofitFactory.createRetrofitService(tClass);
    }

    /**
     * 获取Observable对象，
     * 此处名称的get为获取的意思，不是数据请求方式
     *
     * @param observable
     * @param iResponse
     * @param cacheMap
     * @param <T>
     * @return
     */
    private static <T> Observable<ResponseBody> getObservable(Observable<ResponseBody> observable, IResponse<T> iResponse, Map cacheMap) {
        if (cacheMap != null && cacheMap.size() > 0) {
            CacherManager.addCache(cacheMap.get("chcaeKey").toString(), observable, (int) cacheMap.get("period"));
        }
        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new BaseObserver<>(iResponse));
        return observable;
    }

    private static <T> void cacheData(String url, Map map, IResponse<T> iResponse, int period, boolean isGet) {
        String cacheKey = url + getCacheKey(map);
        CacheObject data = CacherManager.getData(cacheKey);
        if (data == null) {
            Map cacheMap = new HashMap<>();
            cacheMap.put("cacheKey", cacheKey);
            cacheMap.put("period", period);
//            if (isGet) {
//                getDispose(url, map, iResponse, cacheMap);
//            } else {
//                postDispose(url, map, iResponse, cacheMap);
//            }

        } else {
            Map cacheMap = new HashMap();
            cacheMap.put("cacheKey", cacheKey);
            cacheMap.put("period", period);
//            if (isGet) {
//                getDispose(url, map, iResponse, cacheMap);
//            } else {
//                postDispose(url, map, iResponse, cacheMap);
//            }
        }
    }

    private static String getCacheKey(Map param) {
        if (param == null) {
            return "";
        }
        StringBuffer sb = new StringBuffer("");
        TreeMap treeMap = new TreeMap<>(param);
        for (Object key : treeMap.keySet()) {
            /**
             * 过滤掉token，根据自己的需要
             */
            if (!key.toString().equals("token")) {
                sb.append(key).append("=").append(Uri.encode(treeMap.get(key).toString()));
            }
        }
        return sb.toString();
    }
}

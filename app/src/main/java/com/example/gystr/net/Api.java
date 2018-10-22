package com.example.gystr.net;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

public interface Api {
//    /v1/articlePlan/list?num=" + num + "&userId=" + userId;

    public final String HTTP_TOOT_URL = "http://api.suipaohealthy.com";

    @GET("/v1/articlePlan/list")
    Observable<PlanBean> getPlan(@Header("Authorization") String header,@Query("num") String num, @Query("userId") String userId);
}

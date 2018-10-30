package com.example.rr.base;

import com.example.rr.exception.APIException;
import com.example.rr.interfaces.IResponse;
import com.example.rr.utils.ReflectUtil;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;

public class BaseObserver<T> implements Observer<ResponseBody> {

    private final IResponse iResponse;
    private final Gson mGson;
    private final Type finalNeedType;

    public BaseObserver(IResponse<T> iResponse) {
        this.iResponse = iResponse;
        mGson = new Gson();
        /**
         * 通过反射，拿到类中的所有的Interface
         */
        Type[] types = iResponse.getClass().getGenericInterfaces();

        if (ReflectUtil.MethodHandler(types) == null || ReflectUtil.MethodHandler(types).size() == 0) {

        }
        finalNeedType = ReflectUtil.MethodHandler(types).get(0);
    }

    @Override
    public void onSubscribe(Disposable d) {

    }

    @Override
    public void onNext(ResponseBody responseBody) {
        try {

            String result = responseBody.string();
            BaseResponse httpResponse;
            if (finalNeedType instanceof ParameterizedType) {
                //ParameterizedType参数化类型，即泛型
                ParameterizedType p = (ParameterizedType) this.finalNeedType;
                //getActualTypeArguments获取参数化类型的数组，泛型可能有多个
                Class c = (Class) p.getActualTypeArguments()[0];
                System.out.println(mGson.fromJson(result, c));
                httpResponse = mGson.fromJson(result, finalNeedType);
                isHaveBaseResponse(httpResponse);
            }
            if (finalNeedType instanceof Class) {
                JsonObject o = new JsonParser().parse(result).getAsJsonObject();
                if (o.get("code") == null) {
                    iResponse.onSuccess(mGson.fromJson(o, finalNeedType));
                } else {
                    int code = o.get("code").getAsInt();
                    if (code == 0) {
                        if (o.get("resuli") == null) {
                            iResponse.onSuccess(mGson.fromJson(o, finalNeedType));
                        } else {
                            iResponse.onSuccess(finalNeedType != String.class ?
                                    mGson.fromJson(o.get("result").getAsJsonObject(), finalNeedType) : o.get("result").toString());
                        }
                    } else {
                        httpResponse = new BaseResponse();
                        httpResponse.code = code;
                        String msg = o.get("message").toString();
                        httpResponse.msg = msg;
                        isHaveBaseResponse(httpResponse);
                    }
                }
            }

        } catch (IOException |JsonSyntaxException e){
            onError(e);
        }
    }

    @Override
    public void onError(Throwable e) {
        iResponse.onError(e);
    }

    @Override
    public void onComplete() {

    }

    private void isHaveBaseResponse(BaseResponse httpResponse) {
        if (httpResponse.code == 0) {
            iResponse.onSuccess(httpResponse);
        } else {
            iResponse.onError(new APIException(httpResponse.code, httpResponse.msg));
        }
    }
}

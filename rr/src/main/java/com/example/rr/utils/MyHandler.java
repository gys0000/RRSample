package com.example.rr.utils;

import android.os.Handler;
import android.os.Message;

import com.example.rr.base.BaseActivity;
import com.example.rr.base.BasePresenter;

import java.lang.ref.WeakReference;


public class MyHandler extends Handler {

    private final WeakReference<BaseActivity> mActivity;

    /**
     * 从Base Activity中提取出来，原来是因为内部类会因是强引用当前类，采用弱引用，避免长生命周期导致内存泄漏
     *
     * @param mActivity
     */
    public MyHandler(BaseActivity mActivity) {
        this.mActivity = new WeakReference<>(mActivity);
    }


    @Override
    public void handleMessage(Message msg) {
        if (mActivity.get() != null) {
            mActivity.get().requestOver(msg);
        }
    }
}

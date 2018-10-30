package com.example.rr.base;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;


public abstract class BasePresenter {
    abstract protected void initBaseData(Context context, Handler handler, BaseInterface iView, Intent intent);

    abstract protected void handMsg(Message msg);
}

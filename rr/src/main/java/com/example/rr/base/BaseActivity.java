package com.example.rr.base;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.rr.utils.MyHandler;
import com.example.rr.view.LoadingDialogFragment;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public abstract class BaseActivity<Pre extends BasePresenter> extends AppCompatActivity {

    protected Pre presenter;

    private static final String DIALOG_LOADING = "DialogLoading";
    private LoadingDialogFragment waitDialog = null;

    private MyHandler mHandler = new MyHandler(this);
    private IntentFilter filter;
    private BroadcastReceiver receiver;
    private Map<String, BroadcastReceiver> mapReceiver = new HashMap<>();
    private boolean mVisible;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setNavigationBarColor(Color.BLACK);
        }
        if (initLayout() != 0) {
            /*
            设置布局
             */
            setContentView(initLayout());
            ButterKnife.bind(this);
        }


        try {
            if (getPsClass() != null) {
                if (getPsClass().newInstance() instanceof BasePresenter) {
                    presenter = ((Pre) getPsClass().newInstance());
                    /**
                     * 把一些必要的数据传给presenter
                     */
                    presenter.initBaseData(this, mHandler, getIView(), getIntent());
                } else {
                    throw new RuntimeException("必须继承Base Presenter");
                }
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        initData();
        initViewAndListen();

    }

    protected void registerReceiver(@NonNull String... filterActions) {
        filter = filter == null ? new IntentFilter() : filter;
        for (String filterAction : filterActions) {
            filter.addAction(filterAction);
        }
        registerReceiver(filter);
    }

    protected void registerReceiver(@NonNull IntentFilter filter) {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                executeReceiver(context, intent);
            }
        };
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, filter);
        mapReceiver.put(getClass().getSimpleName(), receiver);
    }

    /**
     * 接收到广播
     *
     * @param context
     * @param intent
     */
    protected void executeReceiver(Context context, Intent intent) {

    }

    /**
     * setContentView()
     *
     * @return
     */
    abstract protected int initLayout();

    /**
     * @return presenter 此处不能使用pre类型，newInstance()方法是class下的，Pre不能使用newInstance()实例化
     */
    abstract protected Class getPsClass();

    /**
     * 接口回调
     *
     * @return
     */
    abstract protected BaseInterface getIView();

    /**
     * 初始化简单的数据，比如传过来的title
     */
    abstract protected void initData();

    /**
     * 使用比如ButterKnife可以不使用
     */
    abstract protected void initViewAndListen();

    /**
     * 把发送到view层的message传递到presenter层处理，因为采用了rxjava和retrofit，
     * 很多view不再使用handler发送数据，所以没必要写成抽象方法
     *
     * @param msg
     */
    public void requestOver(Message msg) {
        if (presenter != null) {
            presenter.handMsg(msg);
        }
    }

    protected void to(Intent intent) {
        startActivity(intent);
    }

    protected void to(Class<?> T) {
        Intent intent = new Intent(this, T);
        to(intent);
    }

    protected void to(Class<?> T, Bundle bundle) {
        Intent intent = new Intent(this, T);
        intent.putExtras(bundle);
        to(intent);
    }

    @Override
    public void onBackPressed() {
        if (waitDialog != null) {
            hideProcessDialog();
        } else {
            super.onBackPressed();
        }
    }

    public LoadingDialogFragment showProcessDialog() {
        return showProcessDialog("加载中");
    }

    private LoadingDialogFragment showProcessDialog(String msg) {
        if (mVisible) {
            FragmentManager fm = getSupportFragmentManager();
            if (waitDialog == null) {
                waitDialog = LoadingDialogFragment.newInstance(msg);
            }
            if (!waitDialog.isAdded()) {
                waitDialog.show(fm, DIALOG_LOADING);
            }
            return waitDialog;
        }
        return null;
    }

    public void hideProcessDialog() {
        if (mVisible && waitDialog != null) {
            try {
                waitDialog.dismiss();
                waitDialog = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void setVisible(boolean visible) {
        mVisible = visible;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (ev.getAction() == MotionEvent.ACTION_DOWN) {
            View v = getCurrentFocus();
            if (isShouldHideKeyboard(v, ev)) {
                hideKeyboard(v.getWindowToken());
                v.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    private boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击EditText的事件，忽略它。
                return false;
            } else {
                return true;
            }
        }
        return false;
    }

    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    private void hideKeyboard(IBinder token) {
        if (token != null) {
            InputMethodManager im = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        /*
        移除mHandler，避免因为移除mHandler超activity生命周期工作造成内存泄漏
         */
        mHandler.removeCallbacksAndMessages(null);
        if (mapReceiver != null && mapReceiver.size() > 0 && mapReceiver.get(getClass().getSimpleName()) != null) {
            LocalBroadcastManager.getInstance(this).unregisterReceiver(mapReceiver.get(getClass().getSimpleName()));
        }
    }
}

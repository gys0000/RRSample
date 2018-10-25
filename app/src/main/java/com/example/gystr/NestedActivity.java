package com.example.gystr;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gystr.databinding.ActivityNestedBinding;
import com.example.gystr.net.Api;
import com.example.gystr.net.PlanBean;
import com.example.gystr.net.RankingListBean;
import com.example.gystr.net.RetroInt;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class NestedActivity extends AppCompatActivity {

    private ActivityNestedBinding binding;
    private Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_nested);
        binding.setClick(this);
        api = RetroInt.getDefault();
    }

    //需求：有两个接口请求A和B，只有A成功之后才能请求B
    @SuppressLint("CheckResult")
    public void btn1Click() {
        //普通的解决方法
        commonRxjava();
    }

    @SuppressLint("CheckResult")
    private void commonRxjava() {
//        netA();
//        flatMapNet();
        concatMapNet();
        //fromIterable()将list中的数据迭代出来，然后每10毫秒发送一个
        //Observable.fromIterable(list).delay(10,TimeUnit.MILLISECONDS);
    }

    @SuppressLint("CheckResult")
    private void concatMapNet() {
        api.getPlan("10", "b360f43023c34cd4bef95e2a9017a89c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<PlanBean>() {
                    @Override
                    public void accept(PlanBean planBean) throws Exception {
                        //假装做一下操作，也可以不做
                        Log.e("NestedActivity", "getPlan-doOnNext-accept1: " + planBean.isSuccess());
                    }
                })
                .observeOn(Schedulers.io())
                .concatMap(new Function<PlanBean, ObservableSource<RankingListBean>>() {
                    @Override
                    public ObservableSource<RankingListBean> apply(PlanBean planBean) throws Exception {
                        Log.e("NestedActivity", "concatMap: ");
                        return api.getRankList("Bearer 4ce288014b87fcdb155c8e3f5d03f34e");
                    }
                })
                .observeOn(Schedulers.io())
                .subscribe(new Consumer<RankingListBean>() {
                    @Override
                    public void accept(RankingListBean rankingListBean) throws Exception {
                        Log.e("NestedActivity", "getRankList-subscribe-accept1: " + rankingListBean.isSuccess());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("NestedActivity", "getRankList-subscribe-accept2: " + throwable.getMessage());
                    }
                });

    }

    @SuppressLint("CheckResult")
    private void flatMapNet() {
        api.getPlan("10", "b360f43023c34cd4bef95e2a9017a89c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(new Consumer<PlanBean>() {
                    @Override
                    public void accept(PlanBean planBean) throws Exception {
                        //假装做一下操作，也可以不做
                        Log.e("NestedActivity", "getPlan-doOnNext-accept1: " + planBean.isSuccess());
                    }
                })
                .observeOn(Schedulers.io())
                .flatMap(new Function<PlanBean, ObservableSource<RankingListBean>>() {
                    @Override
                    public ObservableSource<RankingListBean> apply(PlanBean planBean) throws Exception {
                        Log.e("NestedActivity", "flatMap: ");
                        return api.getRankList("Bearer 4ce288014b87fcdb155c8e3f5d03f34e");
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankingListBean>() {
                    @Override
                    public void accept(RankingListBean rankingListBean) throws Exception {
                        Log.e("NestedActivity", "getRankList-subscribe-accept1: " + rankingListBean.isSuccess());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("NestedActivity", "getRankList-subscribe-accept2: " + throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void netA() {
//        api.getPlan("Bearer 0013975ca39b4657a55729eb3fa149cd", "10", "b360f43023c34cd4bef95e2a9017a89c")
        api.getPlan("10", "b360f43023c34cd4bef95e2a9017a89c")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<PlanBean>() {
                    @Override
                    public void accept(PlanBean planBean) throws Exception {
                        Log.e("NestedActivity", "getPlan-accept1: " + planBean.isSuccess());
                        if (planBean.isSuccess()) {
                            netB();
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("NestedActivity", "getPlan-accept2: " + throwable.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    private void netB() {
        api.getRankList("Bearer 4ce288014b87fcdb155c8e3f5d03f34e")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<RankingListBean>() {
                    @Override
                    public void accept(RankingListBean rankingListBean) throws Exception {
                        Log.e("NestedActivity", "getRankList-accept1: " + rankingListBean.isSuccess());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.e("NestedActivity", "getRankList-accept2: " + throwable.getMessage());
                    }
                });
    }

}

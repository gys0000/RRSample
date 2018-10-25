package com.example.gystr;

import android.annotation.SuppressLint;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.gystr.databinding.ActivitySampleNetRrBinding;
import com.example.gystr.net.Api;
import com.example.gystr.net.CommonAdapter;
import com.example.gystr.net.PlanBean;
import com.example.gystr.net.RetroInt;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;

public class SampleNetRRActivity extends AppCompatActivity {

    private ActivitySampleNetRrBinding binding;
    private Disposable d;
    private CompositeDisposable compositeDisposable;
    private PlanBean.DataBean planBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample_net_rr);
        //盛放Disposable对象的容器，每当我们得到一个Disposable时可以调用CompositeDisposable.add()将其放置到这个容器中，
        // 在需要切断所有的水管的时候，，比如页面退出的时候，需要切断所有的网络请求，就可以使用CompositeDisposable.clear() 即可切断所有的水管.
        compositeDisposable = new CompositeDisposable();
        binding.setVariable(BR.planData, planBean);
        binding.btnClick.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("CheckResult")
            @Override
            public void onClick(View v) {
                Api api = RetroInt.getDefault();
//                Observable<PlanBean> getPlan = api.getPlan("Bearer 0013975ca39b4657a55729eb3fa149cd", "10", "b360f43023c34cd4bef95e2a9017a89c");
                Observable<PlanBean> getPlan = api.getPlan("10", "b360f43023c34cd4bef95e2a9017a89c");
                Observable<PlanBean> planBeanObservable = getPlan.subscribeOn(Schedulers.io())//上游，被观察者指定所在的线程，只在第一次有用，以后再指定线程，不会起作用
                        .subscribeOn(AndroidSchedulers.mainThread())//不会起作用
                        .observeOn(Schedulers.io())//下游，观察者，每一次指定新线程，就会切换一次新线程
                        .doOnNext(new Consumer<PlanBean>() {
                            @Override
                            public void accept(PlanBean planBean) throws Exception {
                                Log.e("SampleNetRRActivity", "accept1: " + Thread.currentThread().getName());
                            }
                        })
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnNext(new Consumer<PlanBean>() {
                            @Override
                            public void accept(PlanBean planBean) throws Exception {
                                Log.e("SampleNetRRActivity", "accept2: " + Thread.currentThread().getName());
                            }
                        });
                planBeanObservable
                        .subscribe(new Observer<PlanBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                SampleNetRRActivity.this.d = d;
                                compositeDisposable.add(d);
                                Log.e("SampleNetRRActivity", "onSubscribe: ");
                            }

                            @Override
                            public void onNext(PlanBean planBean) {
                                Log.e("SampleNetRRActivity", "onNext: " + Thread.currentThread().getName());
                                SampleNetRRActivity.this.planBean = planBean.getData().get(0);
                                Log.e("SampleNetRRActivity", "onNext: " + planBean.getData().size());
//                                binding.tvContent.setText(planBean.getData().get(0).getPlanName());
                                binding.setVariable(BR.planData, planBean.getData().get(0));
                                CommonAdapter<PlanBean.DataBean> commonAdapter = new CommonAdapter<PlanBean.DataBean>(SampleNetRRActivity.this, planBean.getData(), R.layout.item_view, BR.user);
                                binding.setVariable(BR.adapter, commonAdapter);
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.e("SampleNetRRActivity", "onError: " + e.getMessage());
                            }

                            @Override
                            public void onComplete() {
                                Log.e("SampleNetRRActivity", "onComplete: ");
                            }
                        });
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        d.dispose();
        compositeDisposable.clear();

    }
}

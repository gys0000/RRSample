package com.example.gystr;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gystr.databinding.ActivityMainBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


/**
 * 学习源于：https://www.jianshu.com/p/464fa025229e
 */
public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Disposable d;
    private Observable novel;
    private Observer<String> reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setClick(this);
    }

    private void onObServer() {

        //中断接受
        reader = new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                MainActivity.this.d = d;
                Log.e("MainActivity", "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                if ("连载2".contains(s)) {
                    //中断接受
                    d.dispose();
                    return;
                }
                Log.e("MainActivity", "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MainActivity", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("MainActivity", "onComplete: ");
            }
        };

    }

    public void btnClick() {
        //被观察者
//        novel = Observable.create(new ObservableOnSubscribe<String>() {
//            @Override
//            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
//                emitter.onNext("连载1");
//                emitter.onNext("连载2");
//                emitter.onNext("连载3");
//                emitter.onComplete();
//            }
//        });
//        onObServer();
//        if (reader != null) {
//        novel.subscribe(reader);
//        }

//        onChained();

        startActivity(new Intent(this, SampleNetRRActivity.class));
    }

    private void onChained() {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> e) throws Exception {
                //被观察者制造事件
                e.onNext("事件1");
                e.onNext("事件2");
                e.onNext("事件3");
                e.onNext("事件4");

//                ObservableEmitter  发射器，用来发射事件，有三种事件：onNext,onComplete,onError

            }
            //观察者（Observer） 观察（subscribe） 被观察者（Observable）  发送出来的事件
        }).subscribe(new Observer<String>() {

            @Override
            public void onSubscribe(Disposable d) {
                // Disposable 用来中断观察者 观察 被观察者 发来的事件
                Log.e("MainActivity", "onSubscribe: ");
            }

            @Override
            public void onNext(String s) {
                Log.e("MainActivity", "onNext: " + s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MainActivity", "onError: " + e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("MainActivity", "onComplete: ");
            }
        });
    }
}

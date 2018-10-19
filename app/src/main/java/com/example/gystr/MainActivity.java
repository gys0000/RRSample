package com.example.gystr;

import android.databinding.DataBindingUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.gystr.bindingUtil.BindingClick;
import com.example.gystr.databinding.ActivityMainBinding;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;


public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Disposable d;
    private Observable novel;
    private Observer<String> reader;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.setClick(new MainActivity());

        onObServer();
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
                if ("2".contains(s)) {
                    //中断接受
                    d.dispose();
                    return;
                }
                Log.e("MainActivity", "onNext: "+s);
            }

            @Override
            public void onError(Throwable e) {
                Log.e("MainActivity", "onError: "+e.getMessage());
            }

            @Override
            public void onComplete() {
                Log.e("MainActivity", "onComplete: ");
            }
        };

    }

    public void btnClick(){
        //被观察者
        novel = Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                emitter.onNext("连载1");
                emitter.onNext("连载2");
                emitter.onNext("连载3");
                emitter.onComplete();
            }
        });
        novel.subscribe(reader);
    }
}

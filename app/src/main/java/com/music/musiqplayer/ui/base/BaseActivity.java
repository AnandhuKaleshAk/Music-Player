package com.music.musiqplayer.ui.base;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.utils.CommonUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseActivity extends AppCompatActivity implements MvpView {

    private ProgressDialog mProgressDialog;
    private static CountDownTimer mCountDownTimer;
    private static final String TAG = "BaseActivity";
    private CompositeDisposable compositeDisposable=new CompositeDisposable();
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this);
    }


    public void startTimer(long milliseconds){

        mCountDownTimer=new CountDownTimer(milliseconds,1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: "+millisUntilFinished);

                ((MyApplication)getApplicationContext())
                        .bus()
                        .send(new EventBus.SendTimer(millisUntilFinished));

            }

            @Override
            public void onFinish() {
                ((MyApplication)getApplicationContext())
                        .bus()
                        .send(new EventBus.SendTimer(0));
                  finish();
                Log.d(TAG, "onFinish: ");
                System.exit(0);
            }
        };
        mCountDownTimer.start();
    }

    public void stopTimer(){
        System.out.println("countdowntimer"+mCountDownTimer);
        if(mCountDownTimer!=null){
            mCountDownTimer.cancel();
        }
        ((MyApplication)getApplicationContext())
                .bus()
                .send(new EventBus.SendTimer(0));

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.dispose();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();

    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }

    @Override
    public boolean isNetworkConnected() {
        return false;
    }

    @Override
    public void hideKeyboard() {

    }



    @Override
    public void addDisposible(Disposable d) {
        compositeDisposable.add(d);
    }
}

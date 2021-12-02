package com.music.darkmusicplayer.ui.base;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;

import com.music.darkmusicplayer.utils.CommonUtils;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseFragment extends Fragment implements MvpView {

    private BaseActivity mActivity;
    private static final String TAG = "BaseFragment";
    private ProgressDialog mProgressDialog;
    private CountDownTimer mCountDownTimer;
    private CompositeDisposable compositeDisposable=new CompositeDisposable();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }


    public void startTimer(long milliseconds){
     mCountDownTimer=new CountDownTimer(milliseconds,1000) {
         @Override
         public void onTick(long millisUntilFinished) {
             Log.d(TAG, "onTick: "+milliseconds);

         }

         @Override
         public void onFinish() {
             Log.d(TAG, "onFinish: ");
         }
     };
     mCountDownTimer.start();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }


    @Override
    public void showLoading() {
        hideLoading();
        mProgressDialog = CommonUtils.showLoadingDialog(this.getContext());
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
    }



    @Override
    public boolean isNetworkConnected() {
        if (mActivity != null) {
            return mActivity.isNetworkConnected();
        }
        return false;
    }

    @Override
    public void onDetach() {
        mActivity = null;
        compositeDisposable.dispose();
        super.onDetach();
    }

    @Override
    public void hideKeyboard() {
        if (mActivity != null) {
            mActivity.hideKeyboard();
        }
    }

    @Override
    public void addDisposible(Disposable d) {
        compositeDisposable.add(d);
    }


}

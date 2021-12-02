package com.music.darkmusicplayer.ui.base;

import android.content.Context;

import io.reactivex.disposables.Disposable;

public interface MvpView {

    void showLoading();

    void hideLoading();

    boolean isNetworkConnected();

    void hideKeyboard();

    void addDisposible(Disposable d);

    Context getContext();
}

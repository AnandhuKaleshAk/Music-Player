package com.music.musiqplayer.ui.main;

import android.content.Context;
import android.util.Log;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class MainPresenter implements IMainMvpPresenter {

    private IMainMvpView mainMvpView;
    private static final String TAG = "MainPresenter";
    private AppRepository mAppRepository;

    public MainPresenter(IMainMvpView mainMvpView, Context context) {
        this.mainMvpView = mainMvpView;
        mAppRepository = new AppRepository(context);
    }

    @Override
    public void onDrawerHomeClicked() {
        mainMvpView.showHomeFragment();

    }

    @Override
    public void onDrawerEquilizerClicked() {
        mainMvpView.showEqualizerFragment();
    }

    @Override
    public void onDrawerSettingsClicked() {
        mainMvpView.showSettingsFragment();
    }

    @Override
    public void onDrawerTimerClicked() {
        mainMvpView.showSleepFragment();
    }

    @Override
    public void onDrawerShareAppClicked() {
        mainMvpView.showShareAppFragment();
    }

    @Override
    public void onFavoriteClicked(Song song) {
        System.out.println("favourite"+song);
        Disposable mFavouriteDisposible = mAppRepository.insertFavourites(!song.isFavorite(), song.getTitle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            getFavouriteSongs(song);
                        }

                );
        mainMvpView.addDisposible(mFavouriteDisposible);
    }
    @Override
    public void getFavouriteSongs(Song song) {
        Disposable mGetFavouriteDisposible = mAppRepository.getSong(song.getTitle())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                          mainMvpView.onFavouriteLoaded(result);
                        }

                );
        mainMvpView.addDisposible(mGetFavouriteDisposible);

    }

    @Override
    public void onSavePlayedTime(Song song) {

        Disposable mSavedPlayTimeDisposible=
        mAppRepository.updatePlayTime(System.currentTimeMillis(),song.getTitle())
                       .observeOn(AndroidSchedulers.mainThread())
                       .subscribeOn(Schedulers.io())
                       .subscribe(
                               result->{
                                   Log.d(TAG, "onSavePlayedTime: "+result);
                               }

                       );

        mainMvpView.addDisposible(mSavedPlayTimeDisposible);



    }


}

package com.music.musiqplayer.ui.artists;

import android.util.Log;

import com.music.musiqplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistPresenter implements IArtistListPresenter {

    private IArtistView mIArtistView;
    private AppRepository  mAppRepository;
    private static final String TAG = "ArtistPresenter";

    public ArtistPresenter(IArtistView mIArtistView) {
        this.mIArtistView = mIArtistView;
        mAppRepository=new AppRepository(mIArtistView.getContext());
    }

    @Override
    public void onArtistLoaded() {

        Disposable albumDisposable=mAppRepository.getArtists()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            Log.d(TAG, "onArtistLoaded: "+result);
                            mIArtistView.onArtistLoaded(result);

                        }

                );
        mIArtistView.addDisposible(albumDisposable);



    }
}

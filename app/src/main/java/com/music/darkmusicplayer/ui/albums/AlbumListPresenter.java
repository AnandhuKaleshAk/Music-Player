package com.music.darkmusicplayer.ui.albums;

import android.util.Log;

import com.music.darkmusicplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumListPresenter implements IAlbumListPresenter {

    private IAlbumView mIAlbumView;

    private AppRepository mAppRepository;
    private static final String TAG = "AlbumListPresenter";

    public AlbumListPresenter(IAlbumView mIAlbumView) {
        this.mIAlbumView = mIAlbumView;
        mAppRepository=new AppRepository(mIAlbumView.getContext());
    }

    @Override
    public void onLoadAlbum() {
        Log.d(TAG, "onLoadAlbum: ");
        Disposable albumDisposable=mAppRepository.getAlbums()
                .observeOn(AndroidSchedulers.mainThread())
                 .subscribeOn(Schedulers.io())
                 .subscribe(
                         result -> {
                             mIAlbumView.onAlbumLoaded(result);
                         }

                 );
        mIAlbumView.addDisposible(albumDisposable);

    }

}

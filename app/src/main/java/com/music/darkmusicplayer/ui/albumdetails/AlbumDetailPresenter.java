package com.music.darkmusicplayer.ui.albumdetails;

import android.util.Log;

import com.music.darkmusicplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumDetailPresenter implements IAlbumDetailPresenter {

    private IAlbumDetailView mIAlbumDetailView;
    private AppRepository mAppRepository;
    private static final String TAG = "AlbumDetailPresenter";


    public AlbumDetailPresenter(IAlbumDetailView mIAlbumDetailView) {
        this.mIAlbumDetailView = mIAlbumDetailView;
         mAppRepository=new AppRepository(mIAlbumDetailView.getContext());
    }

    @Override
    public void loadAlbumSongs(String albumId) {

        Disposable albumDetailDisposable=mAppRepository.getAlbumSong(albumId)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            Log.d(TAG, "loadAlbumSongs: "+result.toString());
                            mIAlbumDetailView.onSongsLoaded(result);
                        }
                        );
        mIAlbumDetailView.addDisposible(albumDetailDisposable);

    }
}

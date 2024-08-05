package com.music.musiqplayer.ui.artists.artistdetail;

import android.util.Log;

import com.music.musiqplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class ArtistDetailPresenter implements IArtistDetailPresenter {
    private static final String TAG = "ArtistDetailPresenter";
    private IArtistDetailView mIArtistDetailView;
    private AppRepository mAppRepository;

    public ArtistDetailPresenter(IArtistDetailView mIArtistDetailView) {
        this.mIArtistDetailView = mIArtistDetailView;
        mAppRepository=new AppRepository(mIArtistDetailView.getContext());

    }

    @Override
    public void loadArtistSongs(String artistName) {
        Disposable artistDetailDisposable=mAppRepository.getArtistSong(artistName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            Log.d(TAG, "loadArtistSongs: "+result.size());
                            mIArtistDetailView.onSongsLoaded(result);
                        }

                );
        mIArtistDetailView.addDisposible(artistDetailDisposable);
    }
}

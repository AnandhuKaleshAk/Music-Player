package com.music.musiqplayer.ui.genres;

import com.music.musiqplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenrePresenter implements IGenrePresenter {

    private IGenreView mIGenreView;
    private AppRepository mAppRepository;


    public GenrePresenter(IGenreView mIGenreView) {
        this.mIGenreView = mIGenreView;
        mAppRepository=new AppRepository(mIGenreView.getContext());
    }

    @Override
    public void onGenreLoaded() {

        Disposable genreDisposable=mAppRepository.getGenres()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            mIGenreView.onGenresLoaded(result);

                        }

                );
        mIGenreView.addDisposible(genreDisposable);


    }
}

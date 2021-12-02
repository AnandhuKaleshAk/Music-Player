package com.music.darkmusicplayer.ui.genres.genresdetail;

import com.music.darkmusicplayer.data.source.AppRepository;
import com.music.darkmusicplayer.ui.genres.IGenreView;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class GenreDetailPresenter implements IGenreDetailPresenter {

    private IGenreDetailView mIGenreDetailView;
    private AppRepository mAppRepository;


    public GenreDetailPresenter(IGenreDetailView mIGenreDetailView) {
        this.mIGenreDetailView = mIGenreDetailView;
        mAppRepository=new AppRepository(mIGenreDetailView.getContext());

    }

    @Override
    public void loadGenreSongs(String genreName) {
        System.out.println("genrename"+genreName);
        Disposable genreDetailDisposable=mAppRepository.getGenreSongs(genreName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            System.out.println("result"+result.size());
                            mIGenreDetailView.onSongsLoaded(result);
                        }

                );
        mIGenreDetailView.addDisposible(genreDetailDisposable);


    }
}

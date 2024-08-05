package com.music.musiqplayer.ui.search;

import com.music.musiqplayer.R;
import com.music.musiqplayer.data.source.AppRepository;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class SearchPresenter implements  ISearchPresenter{

   private ISearchView mISearchView;
   private AppRepository mAppRepository;



    public SearchPresenter(ISearchView mISearchView) {
        this.mISearchView = mISearchView;
        mAppRepository=new AppRepository(mISearchView.getContext());
    }


    @Override
    public void loadSearchSongs(String songName) {
        Disposable searchDetailDisposable=mAppRepository.getSearchSong(songName)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result -> {
                            System.out.println("search"+result.size());
                            mISearchView.onSongsLoaded(result);
                        }

                        );
       mISearchView.addDisposible(searchDetailDisposable);
    }
}

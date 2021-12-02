package com.music.darkmusicplayer.ui.search;

import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.SongListAdapter;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.AppRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
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

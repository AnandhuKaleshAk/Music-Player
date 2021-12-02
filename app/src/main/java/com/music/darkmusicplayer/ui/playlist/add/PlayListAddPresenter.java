package com.music.darkmusicplayer.ui.playlist.add;

import android.util.Log;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.AppRepository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayListAddPresenter implements IPlayListAddPresenter {

    private IPlayListAddView mIPlayListAddView;

    private AppRepository mAppRepository;
    private static final String TAG = "PlayListAddPresenter";

    public PlayListAddPresenter(IPlayListAddView mIPlayListAddView) {
        this.mIPlayListAddView = mIPlayListAddView;
        mAppRepository=new AppRepository(mIPlayListAddView.getContext());
    }

    @Override
    public void loadSongs() {

        Disposable mSongDisposable=mAppRepository.getAllSongs()
                     .observeOn(AndroidSchedulers.mainThread())
                      .subscribeOn(Schedulers.io())
                      .subscribe(
                              result->{
                                  mIPlayListAddView.onSongLoaded(result);
                              }
                      );
        mIPlayListAddView.addDisposible(mSongDisposable);

    }

    @Override
    public List<Song> getSelectedSongs(List<Song> songList) {
        List<Song> selectedSongList=new ArrayList<>();

        for(Song song:songList){
            Log.d(TAG, "getSelectedSongs: ");
            if(song.getChecked()){
                selectedSongList.add(song);
            }
        }
        return selectedSongList;
    }

    @Override
    public long insertPlayList(PlayList playList) {
                Disposable mCreatePlayList=mAppRepository.insertPlayList(playList)
                         .observeOn(AndroidSchedulers.mainThread())
                         .subscribeOn(Schedulers.io())
                         .subscribe(
                                 result->{
                                     mIPlayListAddView.onSongAddedPlayList(result);
                                 }
                         );
                mIPlayListAddView.addDisposible(mCreatePlayList);
        return 0;
    }

    public List<Song> selectAllSongs(List<Song> songList){
        List<Song> selectedSongList=new ArrayList<>();
        for(Song song:songList){
                song.setChecked(true);
                selectedSongList.add(song);
        }

        return selectedSongList;
    }

    public List<Song> disSelectAllSongs(List<Song> songList){
        List<Song> selectedSongList=new ArrayList<>();
        for(Song song:songList){
            song.setChecked(false);
            selectedSongList.add(song);
        }

        return selectedSongList;

    }
}

package com.music.darkmusicplayer.ui.playlist;

import android.util.Log;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.AppRepository;
import com.music.darkmusicplayer.ui.folder.IFolderView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayListPresenter implements IPlayListPresenter {


    private IPlayListView mIPlayListView;

    private AppRepository mAppRepository;
    private static final String TAG = "PlayListPresenter";

    public PlayListPresenter(IPlayListView mIPlayListView) {
        this.mIPlayListView = mIPlayListView;
        mAppRepository=new AppRepository(mIPlayListView.getContext());
    }


    @Override
    public void loadFavoriteSongs() {

      Disposable mFavouriteDisposible=mAppRepository.getFavouriteSongs()
                  .observeOn(AndroidSchedulers.mainThread())
                  .subscribeOn(Schedulers.io())
                  .subscribe(
                          result->{
                              mIPlayListView.onFavoriteSongLoaded(result);
                          }

                  );
      mIPlayListView.addDisposible(mFavouriteDisposible);



    }



    @Override
    public void loadRecentAddedSongs() {
        Disposable recentDisposible=mAppRepository.getAllSongs()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map(unsortedList->{
                    List<Song> sortedList=new ArrayList<>(unsortedList);
                    Collections.sort(sortedList, (left, right) -> left.getAddedTime()- right.getAddedTime());
                    Collections.reverse(sortedList);
                    return sortedList;
                })
                .subscribe(
                        result->{
                            mIPlayListView.onRecentlyAddedSongLoaded(result);
                        }
                );

        mIPlayListView.addDisposible(recentDisposible);
    }

    @Override
    public void loadLastPlayedSongs() {
     Disposable lastPlayedDisposible=mAppRepository.getLastPlayed()
                   .observeOn(AndroidSchedulers.mainThread())
                   .subscribeOn(Schedulers.io())
                   .map(unsortedList->{
                       List<Song> sortedList=new ArrayList<>(unsortedList);
                       Collections.sort(sortedList, (left, right) -> left.getPlayedTime()- right.getPlayedTime());
                       Collections.reverse(sortedList);
                       return sortedList;
                   }).subscribe(
                        result->{
                            mIPlayListView.onLastPlayedSongLoaded(result);
                        }

                   );

       mIPlayListView.addDisposible(lastPlayedDisposible);
    }



    @Override
    public void loadPlayList(){
        Disposable getPlayListDisposible=mAppRepository.getPlaylist()
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribeOn(Schedulers.io())
                             .subscribe(
                                     result->{
                                         mIPlayListView.onPlayListSelected(result);
                                     }


                             );
        mIPlayListView.addDisposible(getPlayListDisposible);
    }

    @Override
    public void deletePlayList(PlayList playList) {
        Disposable getPlayListDisposible=mAppRepository.deletePlaylist(playList.getId())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(
                        result->{
                            mIPlayListView.onPlayListDeleted();
                        }


                );
        mIPlayListView.addDisposible(getPlayListDisposible);

    }
}

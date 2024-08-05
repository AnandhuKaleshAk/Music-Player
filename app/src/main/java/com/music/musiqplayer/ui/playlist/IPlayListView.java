package com.music.musiqplayer.ui.playlist;

import com.music.musiqplayer.data.model.PlayList;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IPlayListView extends MvpView {

    void onFavoriteSongLoaded(List<Song> mSongList);

    void onLastPlayedSongLoaded(List<Song> mSongList);

    void onRecentlyAddedSongLoaded(List<Song> mSongList);

    void onPlayListSelected(List<PlayList> playLists);

    void onPlayListDeleted();

}

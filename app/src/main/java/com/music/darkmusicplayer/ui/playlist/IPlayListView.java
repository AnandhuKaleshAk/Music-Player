package com.music.darkmusicplayer.ui.playlist;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IPlayListView extends MvpView {

    void onFavoriteSongLoaded(List<Song> mSongList);

    void onLastPlayedSongLoaded(List<Song> mSongList);

    void onRecentlyAddedSongLoaded(List<Song> mSongList);

    void onPlayListSelected(List<PlayList> playLists);

    void onPlayListDeleted();

}

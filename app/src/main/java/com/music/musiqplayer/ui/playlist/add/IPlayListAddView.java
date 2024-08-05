package com.music.musiqplayer.ui.playlist.add;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;


public interface IPlayListAddView extends MvpView {

    void onSongLoaded(List<Song> mSongList);

    void onSongAddedPlayList(long status);
}

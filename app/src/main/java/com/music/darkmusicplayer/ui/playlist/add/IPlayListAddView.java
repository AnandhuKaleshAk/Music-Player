package com.music.darkmusicplayer.ui.playlist.add;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;


public interface IPlayListAddView extends MvpView {

    void onSongLoaded(List<Song> mSongList);

    void onSongAddedPlayList(long status);
}

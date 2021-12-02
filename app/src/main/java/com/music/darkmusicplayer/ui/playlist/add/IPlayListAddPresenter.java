package com.music.darkmusicplayer.ui.playlist.add;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;

import java.util.List;

public interface IPlayListAddPresenter {

    void loadSongs();

    List<Song> getSelectedSongs(List<Song> songList);

   long insertPlayList(PlayList playList);

}

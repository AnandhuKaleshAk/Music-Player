package com.music.musiqplayer.ui.playlist.add;

import com.music.musiqplayer.data.model.PlayList;
import com.music.musiqplayer.data.model.Song;

import java.util.List;

public interface IPlayListAddPresenter {

    void loadSongs();

    List<Song> getSelectedSongs(List<Song> songList);

   long insertPlayList(PlayList playList);

}

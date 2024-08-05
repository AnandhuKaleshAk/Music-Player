package com.music.musiqplayer.ui.playlist;

import com.music.musiqplayer.data.model.PlayList;

public interface IPlayListPresenter {
    void loadFavoriteSongs();
    void loadRecentAddedSongs();
    void loadLastPlayedSongs();
    void loadPlayList();
    void deletePlayList(PlayList playList);
}

package com.music.darkmusicplayer.ui.playlist;

import com.music.darkmusicplayer.data.model.PlayList;

public interface IPlayListPresenter {
    void loadFavoriteSongs();
    void loadRecentAddedSongs();
    void loadLastPlayedSongs();
    void loadPlayList();
    void deletePlayList(PlayList playList);
}

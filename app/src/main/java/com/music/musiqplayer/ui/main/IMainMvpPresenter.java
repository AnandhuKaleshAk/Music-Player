package com.music.musiqplayer.ui.main;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpPresenter;

public interface IMainMvpPresenter extends MvpPresenter {

    void onDrawerHomeClicked();

    void onDrawerEquilizerClicked();

    void onDrawerSettingsClicked();

    void onDrawerTimerClicked();

    void onDrawerShareAppClicked();

    void onFavoriteClicked(Song song);

    void getFavouriteSongs(Song song);

    void onSavePlayedTime(Song song);

}

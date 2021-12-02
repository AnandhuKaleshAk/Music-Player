package com.music.darkmusicplayer.ui.main;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpPresenter;

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

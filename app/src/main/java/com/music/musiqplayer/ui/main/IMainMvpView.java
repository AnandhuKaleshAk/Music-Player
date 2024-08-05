package com.music.musiqplayer.ui.main;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

public interface IMainMvpView extends MvpView {


    void onFavouriteLoaded(Song song);

    void showHomeFragment();

    void showEqualizerFragment();

    void showSleepFragment();

    void showSettingsFragment();

    void showShareAppFragment();

    void closeNavigationDrawer();

    void lockDrawer();

    void unlockDrawer();



}

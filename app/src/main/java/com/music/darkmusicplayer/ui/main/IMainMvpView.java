package com.music.darkmusicplayer.ui.main;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

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

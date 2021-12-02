package com.music.darkmusicplayer.ui.sleeptimer;

import com.music.darkmusicplayer.ui.search.ISearchPresenter;

public class SleepTimerPresenter implements ISleepTimerPresenter {

    private ISleepTimerView mISleepTimerView;

    public SleepTimerPresenter(ISleepTimerView mISleepTimerView) {
        this.mISleepTimerView = mISleepTimerView;
    }
}

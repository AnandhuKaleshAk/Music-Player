package com.music.musiqplayer.ui.sleeptimer;

public class SleepTimerPresenter implements ISleepTimerPresenter {

    private ISleepTimerView mISleepTimerView;

    public SleepTimerPresenter(ISleepTimerView mISleepTimerView) {
        this.mISleepTimerView = mISleepTimerView;
    }
}

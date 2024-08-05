package com.music.musiqplayer.data.model;

public class SleepTimer {
    private String sleepTime;
    private Boolean isChecked;

    public String getSleepTime() {
        return sleepTime;
    }

    public Boolean getChecked() {
        return isChecked;
    }

    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public SleepTimer(String sleepTime, Boolean isChecked) {
        this.sleepTime = sleepTime;
        this.isChecked = isChecked;
    }
}

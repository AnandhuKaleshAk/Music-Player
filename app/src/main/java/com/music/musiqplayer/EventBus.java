package com.music.musiqplayer;

import com.music.musiqplayer.data.model.Song;

import java.util.List;

public class EventBus {

    public static class SendSongList {
        private int mPosition;
        private List<Song> mSongList;

        public SendSongList(int mPosition, List<Song> mSongList) {
            this.mPosition = mPosition;
            this.mSongList = mSongList;
        }

        public int getmPosition() {
            return mPosition;
        }

        public List<Song> getmSongList() {
            return mSongList;
        }

    }

    public static class UpdateSongList {
        private int mPosition; private List<Song> mSongList;

        public UpdateSongList(List<Song> mSongList) {
            this.mSongList = mSongList;
        }
        public List<Song> getmSongList() {
            return mSongList;
        }

    }


    public static class SendDeleteList {
        private int mPosition;
        private List<Song> mSongList;

        public SendDeleteList(int mPosition, List<Song> mSongList) {
            this.mPosition = mPosition;
            this.mSongList = mSongList;
        }

        public int getmPosition() {
            return mPosition;
        }

        public List<Song> getmSongList() {
            return mSongList;
        }

    }

    public static class sendDisableEnablePager {
        private Boolean isDisablePlayList;

        public sendDisableEnablePager(Boolean isDisablePlayList) {
            this.isDisablePlayList = isDisablePlayList;
        }

        public Boolean getDisablePlayList() {
            return isDisablePlayList;
        }
    }





    public static class SendSongService {
        private int mPosition;
        private List<Song> mSongList;

        public SendSongService(int mPosition, List<Song> mSongList) {
            this.mPosition = mPosition;
            this.mSongList = mSongList;
        }

        public int getmPosition() {
            return mPosition;
        }

        public List<Song> getmSongList() {
            return mSongList;
        }
    }

    public static class hideUIActivity{
        private Boolean status;

        public hideUIActivity(Boolean status) {
            this.status = status;
        }

        public Boolean getStatus() {
            return status;
        }
    }


    public static class LoadFavourites{
        private Boolean status;

        public LoadFavourites(Boolean status) {
            this.status = status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }

    public static class LoadPlayList{

        private Boolean status;

        public LoadPlayList(Boolean status) {
            this.status = status;
        }

        public void setStatus(Boolean status) {
            this.status = status;
        }
    }

    public static class PlayPause{
        private int mPosition;

        public PlayPause(int mPosition) {
            this.mPosition = mPosition;
        }

        public int getmPosition() {
            return mPosition;
        }

        public void setmPosition(int mPosition) {
            this.mPosition = mPosition;
        }
    }

    public static class SlideLayout{
        private boolean iSlideLayout;

        public void setiSlideLayout(boolean iSlideLayout) {
            this.iSlideLayout = iSlideLayout;
        }

        public boolean isiSlideLayout() {
            return iSlideLayout;
        }

        public SlideLayout(boolean iSlideLayout) {
            this.iSlideLayout = iSlideLayout;
        }
    }



    public static class StopMedia{
        private int mPosition;

        public StopMedia(int mPosition) {
            this.mPosition = mPosition;
        }

        public int getmPosition() {
            return mPosition;
        }

        public void setmPosition(int mPosition) {
            this.mPosition = mPosition;
        }
    }


    public static class SendTimer{
        private long timerSeconds;

        public SendTimer(long timerSeconds) {
            this.timerSeconds = timerSeconds;
        }

        public long getTimerSeconds() {
            return timerSeconds;
        }

        public void setTimerSeconds(long timerSeconds) {
            this.timerSeconds = timerSeconds;
        }
    }


    public static class sendAddedSongs{
        private int mPosition;
        private List<Song> mSong;

        public sendAddedSongs(int mPosition, List<Song> mSong) {
            this.mPosition = mPosition;
            this.mSong = mSong;
        }

        public int getmPosition() {
            return mPosition;
        }
        public List<Song> getmSong() {
            return mSong;
        }
    }

    public static class sendSelectedFragment{
        private int mPosition;
        private Boolean isLoading=false;
        private String fragmentName;


        public sendSelectedFragment(int mPosition, Boolean isLoading, String fragmentName) {
            this.mPosition = mPosition;
            this.isLoading = isLoading;
            this.fragmentName = fragmentName;
        }

        public int getmPosition() {
            return mPosition;
        }

        public Boolean getLoading() {
            return isLoading;
        }

        public String getFragmentName() {
            return fragmentName;
        }
    }


    public static class sendProgress{
        private int duration;
        private int  currentDuration;
        private PlaybackStatus mPlaybackStatus;


        public sendProgress(int duration, int currentDuration, PlaybackStatus mPlaybackStatus) {
            this.duration = duration;
            this.currentDuration = currentDuration;
            this.mPlaybackStatus = mPlaybackStatus;
        }

        public int getDuration() {
            return duration;
        }

        public void setDuration(int duration) {
            this.duration = duration;
        }

        public int getCurrentDuration() {
            return currentDuration;
        }

        public void setCurrentDuration(int currentDuration) {
            this.currentDuration = currentDuration;
        }

        public PlaybackStatus getmPlaybackStatus() {
            return mPlaybackStatus;
        }

        public void setmPlaybackStatus(PlaybackStatus mPlaybackStatus) {
            this.mPlaybackStatus = mPlaybackStatus;
        }
    }
}

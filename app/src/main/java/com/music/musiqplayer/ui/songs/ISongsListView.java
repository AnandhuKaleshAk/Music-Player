package com.music.musiqplayer.ui.songs;

import android.content.Context;

import androidx.loader.app.LoaderManager;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;


public interface ISongsListView extends MvpView {

    void emptyView(boolean visible);

    void handleError(Throwable error);

    Context getContext();

    LoaderManager getLoaderManager();

    void onLocalMusicLoaded(List<Song> songs);

}
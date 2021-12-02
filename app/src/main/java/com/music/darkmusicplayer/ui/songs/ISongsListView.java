package com.music.darkmusicplayer.ui.songs;

import android.content.Context;

import androidx.loader.app.LoaderManager;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;


public interface ISongsListView extends MvpView {

    void emptyView(boolean visible);

    void handleError(Throwable error);

    Context getContext();

    LoaderManager getLoaderManager();

    void onLocalMusicLoaded(List<Song> songs);

}
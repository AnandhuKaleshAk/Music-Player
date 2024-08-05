package com.music.musiqplayer.ui.search;

import android.content.Context;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface ISearchView extends MvpView {

    void onSongsLoaded(List<Song> songs);

    Context getContext();
}

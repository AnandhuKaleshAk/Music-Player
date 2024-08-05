package com.music.musiqplayer.ui.genres.genresdetail;

import android.content.Context;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IGenreDetailView extends MvpView {

    void onSongsLoaded(List<Song> songs);

    Context getContext();
}

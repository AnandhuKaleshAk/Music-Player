package com.music.musiqplayer.ui.genres;

import android.content.Context;

import com.music.musiqplayer.data.model.Genres;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IGenreView extends MvpView {
    void onGenresLoaded(List<Genres> genresList);

    Context getContext();
}

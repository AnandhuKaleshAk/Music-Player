package com.music.darkmusicplayer.ui.genres;

import android.content.Context;

import com.music.darkmusicplayer.data.model.Artist;
import com.music.darkmusicplayer.data.model.Genres;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IGenreView extends MvpView {
    void onGenresLoaded(List<Genres> genresList);

    Context getContext();
}

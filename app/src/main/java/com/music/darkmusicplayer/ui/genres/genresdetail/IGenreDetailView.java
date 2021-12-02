package com.music.darkmusicplayer.ui.genres.genresdetail;

import android.content.Context;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IGenreDetailView extends MvpView {

    void onSongsLoaded(List<Song> songs);

    Context getContext();
}

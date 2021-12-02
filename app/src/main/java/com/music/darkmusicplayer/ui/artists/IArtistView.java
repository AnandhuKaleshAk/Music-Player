package com.music.darkmusicplayer.ui.artists;

import android.content.Context;

import com.music.darkmusicplayer.data.model.Album;
import com.music.darkmusicplayer.data.model.Artist;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IArtistView extends MvpView {

    void onArtistLoaded(List<Artist> songs);

    Context getContext();
}

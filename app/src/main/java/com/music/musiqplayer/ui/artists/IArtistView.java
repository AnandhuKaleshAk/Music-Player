package com.music.musiqplayer.ui.artists;

import android.content.Context;

import com.music.musiqplayer.data.model.Artist;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IArtistView extends MvpView {

    void onArtistLoaded(List<Artist> songs);

    Context getContext();
}

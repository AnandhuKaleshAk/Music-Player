package com.music.darkmusicplayer.ui.albums;

import android.content.Context;

import com.music.darkmusicplayer.data.model.Album;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IAlbumView extends MvpView {

    void onAlbumLoaded(List<Album> songs);

    Context getContext();
}

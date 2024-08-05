package com.music.musiqplayer.ui.albums;

import android.content.Context;

import com.music.musiqplayer.data.model.Album;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IAlbumView extends MvpView {

    void onAlbumLoaded(List<Album> songs);

    Context getContext();
}

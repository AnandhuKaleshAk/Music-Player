package com.music.musiqplayer.ui.albumdetails;

import android.content.Context;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IAlbumDetailView extends MvpView {

    void onSongsLoaded(List<Song> songs);

    Context getContext();
}

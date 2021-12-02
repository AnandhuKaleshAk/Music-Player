package com.music.darkmusicplayer.ui.artists.artistdetail;

import android.content.Context;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IArtistDetailView extends MvpView {

    void onSongsLoaded(List<Song> songs);

    Context getContext();
}

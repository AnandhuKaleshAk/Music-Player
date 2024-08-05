package com.music.musiqplayer.data.source.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.music.musiqplayer.data.model.PlayList;

import java.util.List;

@Dao
public interface PlayListDao {

    @Insert()
    long insert(PlayList playList);

    @Query("SELECT *  from playlist")
    List<PlayList> getPlayList();


    @Update()
    int updatePlayList(PlayList playList);

    @Query("DELETE  from playlist  WHERE id=:playlistid")
    int deletePlaylist(int playlistid);

}

package com.music.musiqplayer.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.music.musiqplayer.data.source.DataConverter;

import java.io.Serializable;
import java.util.List;


@Entity(tableName = "playlist")
public class PlayList implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String playListName;

    private int noOfSongs;

    @TypeConverters(DataConverter.class)
    private List<Song> songs;

    private  int imageId;

    public int getImageId() {
        return imageId;
    }


    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPlayListName() {
        return playListName;
    }

    public void setPlayListName(String playListName) {
        this.playListName = playListName;
    }

    public int getNoOfSongs() {
        return noOfSongs;
    }

    public void setNoOfSongs(int noOfSongs) {
        this.noOfSongs = noOfSongs;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }
}

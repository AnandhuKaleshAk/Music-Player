package com.music.musiqplayer.data.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.music.musiqplayer.data.source.DataConverter;

import java.io.Serializable;
import java.util.List;

@Entity(tableName = "folder")
public class Folder implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;


    private String name;

    private String path;

    private int noOfSongs;

    @TypeConverters(DataConverter.class)
    private List<Song> songs;


    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public int getNoOfSongs() {
        return noOfSongs;
    }

    public void setNoOfSongs(int noOfSongs) {
        this.noOfSongs = noOfSongs;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}

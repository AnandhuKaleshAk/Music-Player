package com.music.darkmusicplayer.data.model;


import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "song")
public class Song implements Serializable {

    @PrimaryKey(autoGenerate = true)

    private int id;

    private int songId;


    private String title;

    private String displayName;

    private String artist;

    private String album;

    private String path;

    private int duration;

    private int size;

    private boolean favorite;

    private long albumID;

    private int addedTime;

    private int playedTime;
    private String genreName;


    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }

    @Ignore
    private Boolean isChecked=false;

    public Boolean getChecked() {
        return isChecked;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }


    public void setChecked(Boolean checked) {
        isChecked = checked;
    }

    public int getPlayedTime() {
        return playedTime;
    }


    public void setPlayedTime(int playedTime) {
        this.playedTime = playedTime;
    }

    public int getAddedTime() {
        return addedTime;
    }

    public void setAddedTime(int addedTime) {
        this.addedTime = addedTime;
    }

    public long getAlbumID() {
        return albumID;
    }

    public void setAlbumID(long albumID) {
        this.albumID = albumID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getDuration() {
        return duration;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public boolean isFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }
}

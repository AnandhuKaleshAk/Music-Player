package com.music.darkmusicplayer.data.source.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import com.music.darkmusicplayer.data.model.Album;
import com.music.darkmusicplayer.data.model.Artist;
import com.music.darkmusicplayer.data.model.Genres;
import com.music.darkmusicplayer.data.model.Song;

import java.util.List;

@Dao
public interface SongDao {

    @Insert()
    void insert(Song song);

    @Query("SELECT DISTINCT albumID,album from song")
    List<Album> getAlbums();

    @Query("SELECT DISTINCT artist,albumID,album from song")
    List<Artist> getArtist();

    @Query("SELECT DISTINCT albumID,album,genreName from song")
    List<Genres> getGenres();


    @Query("SELECT *  from song WHERE title=:title")
    Song getSong(String title);

    @Query("SELECT *  from song WHERE albumID=:albumID")
    List<Song> getAlbumSong(String albumID);

    @Query("SELECT *  from song WHERE artist=:artistName")
    List<Song> getArtistSong(String artistName);

    @Query("SELECT *  from song WHERE title LIKE '%' || :songName || '%'")
    List<Song> getSearchSong(String songName);


    @Query("SELECT *  from song WHERE genreName=:genreName")
    List<Song> getGenreSongs(String genreName);


    @Query("SELECT *  from song")
    List<Song> getAllSong();

    @Query("SELECT *  from song WHERE playedTime!=0")
    List<Song> getLastPlayed();

    @Query("SELECT *  from song WHERE favorite=1")
    List<Song> getFavouriteSong();

    @Query("UPDATE song set favorite=:val WHERE title=:title")
    int insertFavourites(boolean val, String title);

    @Query("UPDATE song set playedTime=:playTime WHERE title=:title")
    int updatePlayTime(long playTime,String title);


}

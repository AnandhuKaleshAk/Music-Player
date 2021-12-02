package com.music.darkmusicplayer.data.source.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.DataConverter;

import java.util.List;

@Dao
public interface FolderDao {

    @Insert()
    void insert(Folder folder);

    @Query("SELECT *  from folder")
    List<Folder> getFolder();

    @Query("DELETE  from folder")
    void deleteFolder();
}

package com.music.musiqplayer.data.source.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.music.musiqplayer.data.model.Folder;

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

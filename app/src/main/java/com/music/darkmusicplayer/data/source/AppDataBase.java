package com.music.darkmusicplayer.data.source;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.dao.FolderDao;
import com.music.darkmusicplayer.data.source.dao.PlayListDao;
import com.music.darkmusicplayer.data.source.dao.SongDao;


@Database(entities = {Song.class,Folder.class, PlayList.class}, version = 1)
public abstract class AppDataBase extends RoomDatabase {

    private static AppDataBase mInstance;

    public abstract SongDao songDao();

    public abstract FolderDao folderDao();

    public abstract PlayListDao playListDao();



    public static synchronized AppDataBase getInstance(Context context) {
        if (mInstance == null) {
            mInstance = Room.databaseBuilder(context.getApplicationContext(), AppDataBase.class, "song_database")
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();

        }
        return mInstance;
    }

}

package com.music.darkmusicplayer.data.source;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.Song;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class DataConverter implements Serializable {

    @TypeConverter
    public  String fromFolderList(List<Song> songList){
        Gson gson=new Gson();
        String json=gson.toJson(songList);
        return json;
    }

    @TypeConverter
    public  List<Song>  toFolderList(String songValues){
        if(songValues==null){
           return null;
        }
        Gson gson=new Gson();

        Type type=new TypeToken<List<Song>>(){
        }.getType();
        List<Song>  folderList=gson.fromJson(songValues,type);
       return folderList;
    }



}

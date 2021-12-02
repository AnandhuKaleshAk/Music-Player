package com.music.darkmusicplayer.utils;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.music.darkmusicplayer.data.model.Folder;


import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


public class DBUtils {

    private static final String TAG = "DBUtils";
    public static List<Folder> generateDefaultFolders() {
        List<Folder> defaultFolders = new ArrayList<>(3);
        File sdcardDir = Environment.getExternalStorageDirectory();

        HashSet<File> fileList=new HashSet<>();
        HashSet<File> mSdCardFolderList=FileUtils.foldersFromSdCard(sdcardDir,fileList);

        for(File file:mSdCardFolderList){
            Log.d(TAG, "generateDefaultFolders: "+file);

            defaultFolders.add(FileUtils.folderFromDir(file));
        }
        return defaultFolders;
    }
}

package com.music.darkmusicplayer.ui.folder;

import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IFolderView extends MvpView {

    void onFolderLoaded(List<Folder> folderList);

    void onFolderLoadedNotDb(List<Folder> folderList);
}

package com.music.musiqplayer.ui.folder;

import com.music.musiqplayer.data.model.Folder;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IFolderView extends MvpView {

    void onFolderLoaded(List<Folder> folderList);

    void onFolderLoadedNotDb(List<Folder> folderList);
}

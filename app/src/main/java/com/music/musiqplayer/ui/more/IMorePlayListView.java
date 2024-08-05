package com.music.musiqplayer.ui.more;

import com.music.musiqplayer.data.model.PlayList;
import com.music.musiqplayer.ui.base.MvpView;

import java.util.List;

public interface IMorePlayListView extends MvpView {

    void onMorePlayListLoaded(List<PlayList> playListList);

    void onMorPlayListAdded();

    void onMorePlayListCreated();
}

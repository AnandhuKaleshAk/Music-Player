package com.music.darkmusicplayer.ui.more;

import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.ui.base.MvpView;

import java.util.List;

public interface IMorePlayListView extends MvpView {

    void onMorePlayListLoaded(List<PlayList> playListList);

    void onMorPlayListAdded();

    void onMorePlayListCreated();
}

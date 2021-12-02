package com.music.darkmusicplayer.ui.songs;

import android.content.Context;
import android.os.Bundle;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.PlayerService;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.SongListAdapter;
import com.music.darkmusicplayer.adapter.SongPagerAdapter;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.base.BaseFragment;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SongFragments extends BaseFragment implements ISongsListView {


    @BindView(R.id.recycler_view_songList)
    RecyclerView mRecyclerViewSong;

    private static final String TAG = "SongFragments";

    private SongListAdapter mSongListAdapter;
    private static final String FOLDER_SONG= "foldersong";

    private List<Song> mSongList;


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    private void setDefaults() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mSongListAdapter = new SongListAdapter(null, getActivity());
        mRecyclerViewSong.setAdapter(mSongListAdapter);

        Disposable songListDispose = ((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.UpdateSongList) {
                                EventBus.UpdateSongList sendSongList = (EventBus.UpdateSongList) it;
                                mSongList = sendSongList.getmSongList();
                                Log.d(TAG, "onLocalMusicLoaded: "+sendSongList.getmSongList().size());
                                mSongListAdapter.setmSongList(mSongList);
                            }
                        }
                );
        addDisposible(songListDispose);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setDefaults();
        new SongsListPresenter(this).loadLocalMusic();

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void emptyView(boolean visible) {

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void onLocalMusicLoaded(List<Song> songs) {
//        Log.d(TAG, "onLocalMusicLoaded: "+songs.size());
//        mSongListAdapter.setmSongList(songs);


    }




}

package com.music.musiqplayer.ui.playlist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.SongListAdapter;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class PlayListSongsFragment extends BaseFragment {

    private List<Song> mSongList=new ArrayList<>();
    private SongListAdapter mSongListAdapter;

    private static final String PLAYLIST_SONG= "playlistsong";

    @BindView(R.id.recycler_view_songPlayList)
    RecyclerView mRecyclerViewSongPlayList;
    private static final String TAG = "PlayListSongFragment";


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();

    }
    private void init(){


        Bundle bundle=getArguments();
        if(bundle!=null){
            mRecyclerViewSongPlayList.setLayoutManager(new LinearLayoutManager(getActivity()));
            mSongListAdapter = new SongListAdapter(mSongList,getActivity());
            mRecyclerViewSongPlayList.setAdapter(mSongListAdapter);
            mSongList=(List<Song>)bundle.getSerializable(PLAYLIST_SONG);
            mSongListAdapter.setmSongList(mSongList);
        }
    }





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_playlist_songs, container, false);
    }
}
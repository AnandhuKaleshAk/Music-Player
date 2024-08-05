package com.music.musiqplayer.ui.folder;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.AlbumDetailSongAdapter;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class FolderSongFragment extends BaseFragment {

    private List<Song> mSongList=new ArrayList<>();
    private AlbumDetailSongAdapter mSongListAdapter;

    private static final String FOLDER_SONG= "foldersong";

    @BindView(R.id.recycler_view_songFolder)
    RecyclerView mRecyclerViewSongFolder;
    private static final String TAG = "FolderSongFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder_song, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        init();

    }
    private  void init(){
        Bundle bundle=getArguments();
        if(bundle!=null){
            mRecyclerViewSongFolder.setLayoutManager(new LinearLayoutManager(getActivity()));
            mSongListAdapter = new AlbumDetailSongAdapter(mSongList,  getActivity());
            mRecyclerViewSongFolder.setAdapter(mSongListAdapter);
            mSongList=(List<Song>)bundle.getSerializable(FOLDER_SONG);
            mSongListAdapter.setmSongList(mSongList);

        }

    }
}

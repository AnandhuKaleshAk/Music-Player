package com.music.darkmusicplayer.ui.folder;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.FolderAdapter;
import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.albumdetails.AlbumDetailFragment;
import com.music.darkmusicplayer.ui.base.BaseFragment;
import com.music.darkmusicplayer.ui.songs.SongFragments;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FolderFragments extends BaseFragment implements IFolderView, FolderAdapter.OnFolderItemClickedListener {
    // TODO: Rename parameter arguments, choose names that match


    @BindView(R.id.recycler_view_folder)
    RecyclerView mRecyclerViewFolder;

    private List<Folder> mFolderList;

    private FragmentManager mFragmentManager;

    private FolderAdapter mFolderAdapter;
    private static final String TAG = "FolderFragments";
    private static final String FOLDER_SONG = "foldersong";

    private FolderPresenter mFolderPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d(TAG, "onCreateView: ");

        return inflater.inflate(R.layout.fragment_folder, container, false);

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        ButterKnife.bind(this, view);
        setUP();
        mFolderPresenter = new FolderPresenter(this);
        mFolderPresenter.loadSavedFolder();
    }


    private void setUP() {
        mFragmentManager = getFragmentManager();
        mFolderList = new ArrayList<>();
        mRecyclerViewFolder.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFolderAdapter = new FolderAdapter(mFolderList, getActivity());
        mRecyclerViewFolder.setAdapter(mFolderAdapter);
        mFolderAdapter.setFolderList(mFolderList);
        mFolderAdapter.setmOnFolderItemClickedListener(this);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Log.d(TAG, "onAttach: ");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        Log.d(TAG, "onDetach: ");
    }


    @Override
    public void onFolderLoaded(List<Folder> folderList) {
        mFolderAdapter.setFolderList(folderList);

    }

    @Override
    public void onFolderLoadedNotDb(List<Folder> folderList) {
        mFolderAdapter.setFolderList(folderList);
    }

    @Override
    public void onFolderClicked(int position, List<Song> mSongList) {

        Bundle arguments = new Bundle();
        arguments.putSerializable(FOLDER_SONG, (Serializable) mSongList);
        BaseFragment mSongFragment = new AlbumDetailFragment();
        mSongFragment.setArguments(arguments);
        String backToStackName = getClass().getName();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
        fragmentTransaction.addToBackStack(backToStackName);
        fragmentTransaction.replace(R.id.container_folder, mSongFragment);
        fragmentTransaction.commit();

    }

}

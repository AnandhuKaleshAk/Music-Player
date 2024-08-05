package com.music.musiqplayer.ui.more;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.MorePlayListAdapter;
import com.music.musiqplayer.data.model.PlayList;

import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseDialogFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class AddToPlayDialogFragment extends BaseDialogFragment implements IMorePlayListView, MorePlayListAdapter.OnMorePlayListSelected {


    MorePlayListPresenter mMorePlayListPresenter;
    private ArrayList<PlayList> mPlayLists;


    @BindView(R.id.morePlaylist_recyclerview)
    RecyclerView mRecyclerViewMorePlayList;

    private MorePlayListAdapter mMorePlayListAdapter;
    FragmentManager mFragmentManager;

    public static final String SONG = "song";
    Song song;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_playlist, container, false);

    }


    private void initValues() {

        Bundle bundle = getArguments();
        if (bundle != null) {
            song = (Song) bundle.getSerializable(SONG);
        }
        mMorePlayListPresenter = new MorePlayListPresenter(this);
        mFragmentManager = getFragmentManager();
        mPlayLists = new ArrayList<>();
        mRecyclerViewMorePlayList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mMorePlayListAdapter = new MorePlayListAdapter(mPlayLists, getActivity());
        mRecyclerViewMorePlayList.setAdapter(mMorePlayListAdapter);
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        ButterKnife.bind(this, view);
        initValues();
        loadPlayList();

        setEventListerners();
    }

    public void setEventListerners() {
        mMorePlayListAdapter.setMorePlayListSelected(this);
    }

    private void loadPlayList() {
        mMorePlayListPresenter.loadPlayList();
    }


    @OnClick(R.id.add_playlist_moreImage)
    public void createToPlayList() {
        dismiss();
        showPop();
    }

    private void showPop() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
        View mView = layoutInflaterAndroid.inflate(R.layout.playlist_add_dialog, null);
        MaterialAlertDialogBuilder alertDialogBuilderUserInput = new MaterialAlertDialogBuilder(getActivity());
        alertDialogBuilderUserInput.setView(mView);
        final Button createPlaylistButton = mView.findViewById(R.id.create_playlist_button);
        final Button cancelPlaylistButton = mView.findViewById(R.id.cancel_playlist_button);
        final EditText namePlaylistEditText = mView.findViewById(R.id.name_playlist_editText);

        AlertDialog alertDialog = alertDialogBuilderUserInput.create();
        alertDialog.show();

        createPlaylistButton.setOnClickListener(v -> {
            Log.d(TAG, "showPop:"+namePlaylistEditText.getText().toString().trim()+"sds");
            if(!namePlaylistEditText.getText().toString().trim().equals("")) {
                PlayList playList = new PlayList();
                playList.setPlayListName(namePlaylistEditText.getText().toString());
                List<Song> songs=new ArrayList<>();
                songs.add(song);
                playList.setSongs(songs);
                playList.setNoOfSongs(songs.size());
                createPlayList(playList);
                alertDialog.cancel();

            }else{
                Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.text_empty_playlist), Toast.LENGTH_SHORT).show();
            }
        });
        cancelPlaylistButton.setOnClickListener(view -> {
            alertDialog.cancel();
        });
    }

    public void createPlayList(PlayList playList){
        mMorePlayListPresenter.insertPlayList(playList);


    }




    @Override
    public void onResume() {
        super.onResume();
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = ViewGroup.LayoutParams.MATCH_PARENT;
        params.height = ViewGroup.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);

    }

    @Override
    public void onMorePlayListLoaded(List<PlayList> playListList) {
        mMorePlayListAdapter.setPlayList(playListList);
    }

    @Override
    public void onMorPlayListAdded() {

    }

    @Override
    public void onMorePlayListCreated() {


    }

    @Override
    public void onSelected(PlayList playList) {
        Log.d(TAG, "onSelected: songs" + playList.getSongs().size());
        List<Song> songs = playList.getSongs();
        songs.add(song);
        playList.setNoOfSongs(songs.size());
        mMorePlayListPresenter.updatePlayList(playList);
        dismiss();

    }
}

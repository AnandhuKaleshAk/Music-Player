package com.music.darkmusicplayer.ui.playlist;

import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.PlayListAdapter;
import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.albumdetails.AlbumDetailFragment;
import com.music.darkmusicplayer.ui.base.BaseFragment;
import com.music.darkmusicplayer.ui.folder.FolderSongFragment;
import com.music.darkmusicplayer.ui.playlist.add.PlayListAddActivity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static androidx.constraintlayout.widget.Constraints.TAG;


public class PlayListFragment extends BaseFragment implements IPlayListView,PlayListAdapter.OnPlayListItemClickedListener {


    @BindView(R.id.playlist_recyclerview)
    RecyclerView mRecyclerViewPlayList;

    PlayListPresenter mPlayListPresenter;

    private ArrayList<PlayList> mPlayLists;

    private PlayListAdapter mPlayListAdapter;
    FragmentManager mFragmentManager;

    private static final String FOLDER_SONG = "foldersong";

    private static final String PLAYLIST_SONG= "playlistsong";

    private static final String PLAYLIST= "playlist";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_play_list, container, false);
        
    }
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        ButterKnife.bind(this, view);
        initValues();
        loadData();
        setEventListeners();
    }

    private void setEventListeners(){
        mPlayListAdapter.setOnPlayListClickListener(this);
    }

    @OnClick(R.id.add_playlist_image)
     void addPlaylist()
    {
        showPop();


    }

   private  void  showPop(){
       LayoutInflater layoutInflaterAndroid = LayoutInflater.from(getActivity());
       View mView = layoutInflaterAndroid.inflate(R.layout.playlist_add_dialog, null);
       AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(getActivity());
       alertDialogBuilderUserInput.setView(mView);
       final Button createPlaylistButton = mView.findViewById(R.id.create_playlist_button);
       final Button cancelPlaylistButton = mView.findViewById(R.id.cancel_playlist_button);
       final EditText namePlaylistEditText= mView.findViewById(R.id.name_playlist_editText);

       AlertDialog alertDialog = alertDialogBuilderUserInput.create();
       alertDialog.show();

       createPlaylistButton.setOnClickListener(v -> {
           Log.d(TAG, "showPop:"+namePlaylistEditText.getText().toString().trim()+"sds");
           if(!namePlaylistEditText.getText().toString().trim().equals("")) {
               PlayList playList = new PlayList();
               playList.setPlayListName(namePlaylistEditText.getText().toString());
               playList.setNoOfSongs(0);
               Intent intent = new Intent(getActivity(), PlayListAddActivity.class);
               intent.putExtra(PLAYLIST, playList);
               startActivity(intent);
               alertDialog.cancel();
           }else{
               Toast.makeText(getActivity(), getActivity().getResources().getString(R.string.text_empty_playlist), Toast.LENGTH_SHORT).show();
           }
       });
       cancelPlaylistButton.setOnClickListener(view -> {
           alertDialog.cancel();
       });
    }



    private void initValues(){
        mFragmentManager = getFragmentManager();
        mPlayLists=new ArrayList<>();
        mPlayListPresenter=new PlayListPresenter(this);
        mRecyclerViewPlayList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mPlayListAdapter=new PlayListAdapter(mPlayLists,getActivity());
        mRecyclerViewPlayList.setAdapter(mPlayListAdapter);
        mPlayListPresenter.loadFavoriteSongs();

    }


    @Override
    public void onFavoriteSongLoaded(List<Song> mSongList) { ;
        PlayList mPlayList=new PlayList();
        mPlayList.setPlayListName(Objects.requireNonNull(getActivity()).getResources().getString(R.string.text_favourites));
        mPlayList.setNoOfSongs(mSongList.size());
        mPlayList.setSongs(mSongList);
        mPlayList.setImageId(R.drawable.ic_favourite_playlist);
        mPlayLists.add(mPlayList);
        mPlayListAdapter.notifyDataSetChanged();
        mPlayListPresenter.loadRecentAddedSongs();

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    public void loadData(){
        Disposable loadDisposible = ((MyApplication) Objects.requireNonNull(getActivity()).getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it->{
                            if (it instanceof EventBus.LoadFavourites){
                                mPlayLists.clear();
                                mPlayListPresenter.loadFavoriteSongs();
                            }

                        }
                );
        addDisposible(loadDisposible);

    }

    @Override
    public void onLastPlayedSongLoaded(List<Song> mSongList) {
     PlayList mPlayList=new PlayList();
     mPlayList.setPlayListName(Objects.requireNonNull(getActivity()).getResources().getString(R.string.text_last_played));
     mPlayList.setNoOfSongs(mSongList.size());
     mPlayList.setSongs(mSongList);
     mPlayLists.add(mPlayList);
     mPlayListAdapter.notifyDataSetChanged();
     mPlayListPresenter.loadPlayList();

    }

    @Override
    public void onRecentlyAddedSongLoaded(List<Song> mSongList) {
       PlayList mPlayList=new PlayList();
       mPlayList.setPlayListName(Objects.requireNonNull(getActivity()).getResources().getString(R.string.text_recent_added));
       mPlayList.setNoOfSongs(mSongList.size());
        mPlayList.setSongs(mSongList);
       mPlayLists.add(mPlayList);
       mPlayListAdapter.notifyDataSetChanged();
       mPlayListPresenter.loadLastPlayedSongs();
    }

    @Override
    public void onPlayListSelected(List<PlayList> playLists) {
        System.out.println();
        mPlayLists.addAll(playLists);
        mPlayListAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPlayListDeleted() {
        Log.d(TAG, "onPlayListDeleted: ");
    }


    @Override
    public void onPlayListItemSelected(int position, PlayList mPlayLists) {

        Bundle arguments = new Bundle();
        arguments.putSerializable(FOLDER_SONG, (Serializable) mPlayLists.getSongs());
        BaseFragment mSongFragment = new AlbumDetailFragment();
        mSongFragment.setArguments(arguments);
        String backToStackName = getClass().getName();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
        fragmentTransaction.addToBackStack(backToStackName);
        fragmentTransaction.replace(R.id.container_playlist, mSongFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onDeletePlayListSelected(int position, PlayList playList) {
      mPlayListPresenter.deletePlayList(playList);
      mPlayLists.remove(position);
      mPlayListAdapter.notifyDataSetChanged();
    }
}

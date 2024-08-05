package com.music.musiqplayer.ui.playlist.add;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.PlaylistAddAdapter;
import com.music.musiqplayer.data.model.PlayList;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class PlayListAddActivity extends BaseActivity implements IPlayListAddView {

    @BindView(R.id.song_select_recyclerview)
    RecyclerView  mRecyclerViewSelectSong;

    @BindView(R.id.count_selectSong_text)
    TextView mSelectSongCountTextview;

    private PlayListAddPresenter mPlayListAddPresenter;

    private static final String PLAYLIST= "playlist";
    private static final String TAG = "PlayListAddActivity";
    @BindView(R.id.playlist_toolbar)
    Toolbar mPlayListToolBar;

    @BindView(R.id.all_selectSong_checkbox)
    CheckBox mAllSongCheckBox;

    List<Song> mSongList;

    private PlaylistAddAdapter mPlaylistAddAdapter;


    PlayList mPlayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playlist_add);
        ButterKnife.bind(this);
        init();
    }

    private void init(){
        mSongList=new ArrayList<>();
        mPlayListAddPresenter=new PlayListAddPresenter(this);
        mPlayListToolBar.setNavigationIcon(R.drawable.ic_arrow_back);
        mPlayListToolBar.setNavigationOnClickListener(view -> {
          finish();
          callPlayList();
        });
        mPlayList= (PlayList) getIntent().getSerializableExtra(PLAYLIST);
        mRecyclerViewSelectSong.setLayoutManager(new LinearLayoutManager(this));
        mPlaylistAddAdapter=new PlaylistAddAdapter(mSongList,this);
        mRecyclerViewSelectSong.setAdapter(mPlaylistAddAdapter);
        mPlayListAddPresenter.loadSongs();
        getSongs();
        mAllSongCheckBox.setOnClickListener(view -> {
            if(mAllSongCheckBox.isChecked()){
               List<Song> checkedSongs=mPlayListAddPresenter.selectAllSongs(mSongList);
                mPlaylistAddAdapter.setSongList(checkedSongs);
                mSelectSongCountTextview.setText(String.valueOf(checkedSongs.size()));
            }else{
                List<Song> unCheckedSongs=mPlayListAddPresenter.disSelectAllSongs(mSongList);
                mPlaylistAddAdapter.setSongList(unCheckedSongs);
                mSelectSongCountTextview.setText(String.valueOf(0));
            }
        });

    }


    @OnClick(R.id.save_selectSong_image)
    void saveSelectedSongs() {
        List<Song> songList=mPlayListAddPresenter.getSelectedSongs(mSongList);
        mPlayList.setSongs(songList);
        mPlayList.setNoOfSongs(songList.size());
        mPlayListAddPresenter.insertPlayList(mPlayList);

    }

    @OnClick(R.id.discard_selectSong_image)
    void discardSelectedSongs() {
        List<Song> unCheckedSongs=mPlayListAddPresenter.disSelectAllSongs(mSongList);
        mPlaylistAddAdapter.setSongList(unCheckedSongs);
        mSelectSongCountTextview.setText(String.valueOf(0));
        mAllSongCheckBox.setChecked(false);
    }






    private void getSongs(){
        Disposable selectDisposible = ((MyApplication) Objects.requireNonNull(this).getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it->{
                            if (it instanceof EventBus.sendAddedSongs){
                                EventBus.sendAddedSongs songBus=(EventBus.sendAddedSongs)it;
                                 List<Song> songList=mPlayListAddPresenter.getSelectedSongs(songBus.getmSong());
                                 Log.d(TAG, "getSongs: "+songList.size());
                                 mSelectSongCountTextview.setText(String.valueOf(songList.size()));
                            }
                        }
                );
        addDisposible(selectDisposible);
    }



    @Override
    public void onSongLoaded(List<Song> songList) {
        mSongList.clear();
        mSongList=songList;
        mPlaylistAddAdapter.setSongList(mSongList);
    }

    @Override
    public void onSongAddedPlayList(long status) {
        finish();
        callPlayList();
    }

    public void callPlayList(){
        ((MyApplication) getApplicationContext())
                .bus()
                .send(new EventBus.LoadFavourites(true));
    }




    @Override
    public Context getContext() {
        return null;
    }
}

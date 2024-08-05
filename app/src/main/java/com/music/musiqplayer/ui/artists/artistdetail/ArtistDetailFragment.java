package com.music.musiqplayer.ui.artists.artistdetail;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentUris;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.SongListAdapter;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ArtistDetailFragment extends BaseFragment implements IArtistDetailView{



    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbarAlbum;


    @BindView(R.id.recyclerview_album_detail)
    RecyclerView mRecyclerViewSongArtist;

    @BindView(R.id.image_abumdetail_back)
    ImageView mBackAlbumDetailImageView;


    @BindView(R.id.image_albumdetail_cover)
    ImageView mCoverAlbumDetailImageView;

    private static final String ARTIST_NAME = "artistName";
    private static final String ALBUM_ID = "albumID";

    private List<Song> mSongList=new ArrayList<>();
    private SongListAdapter mSongListAdapter;


    private String mArtistName;
    private long mAlbumId;

    private static final String TAG = "ArtistDetailFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideMainActivtyUI();
    }


    public void setToolbarDimension(){
        Display display = getActivity().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        ViewGroup.LayoutParams params = mAppBarLayout.getLayoutParams();
        int height =(outMetrics.heightPixels / 3);
        params.height = height;
        mAppBarLayout.setLayoutParams(params);

    }


    public void hideMainActivtyUI() {
        ((MyApplication) getActivity().getApplicationContext())
                .bus()
                .send(new EventBus.hideUIActivity(true));
    }
    public void resetToolBarUI(){
        ((MyApplication) getActivity().getApplicationContext())
                .bus()
                .send(new EventBus.hideUIActivity(false));
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        init();
        setToolbarDimension();
        ((MyApplication) getActivity().getApplicationContext())
                .bus()
                .send(new EventBus.sendDisableEnablePager(true));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_album_detail, container, false);
    }


    private  void init(){
        mArtistName = getArguments().getString(ARTIST_NAME);
        mAlbumId=getArguments().getLong(ALBUM_ID);
        mRecyclerViewSongArtist.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSongListAdapter = new SongListAdapter(mSongList,  getActivity());
        mRecyclerViewSongArtist.setAdapter(mSongListAdapter);

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");

        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, mAlbumId);
        Glide.with(this)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(mCoverAlbumDetailImageView);
        Log.d(TAG, "init: "+mArtistName);
        new ArtistDetailPresenter(this).loadArtistSongs(mArtistName);

    }


    @OnClick(R.id.image_abumdetail_back)
    public void onBackClicked(){
        Log.d(TAG, "onBackClicked: ");
        resetToolBarUI();
        getFragmentManager().popBackStackImmediate();
    }



    @Override
    public void onSongsLoaded(List<Song> songs) {
        Log.d(TAG, "onSongsLoaded: "+songs.size());
        mSongListAdapter.setmSongList(songs);
        mToolbarAlbum.setTitle("");
    }
}
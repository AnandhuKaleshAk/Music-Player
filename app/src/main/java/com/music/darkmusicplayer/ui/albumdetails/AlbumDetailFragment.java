package com.music.darkmusicplayer.ui.albumdetails;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.AppBarLayout;
import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.R;
import com.music.darkmusicplayer.adapter.AlbumDetailSongAdapter;
import com.music.darkmusicplayer.adapter.SongListAdapter;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.albums.AlbumFragment;
import com.music.darkmusicplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class AlbumDetailFragment extends BaseFragment implements IAlbumDetailView  {


    @BindView(R.id.app_bar)
    AppBarLayout mAppBarLayout;

    @BindView(R.id.toolBarRelativeLayout)
    RelativeLayout mToolabarLayout;

    @BindView(R.id.toolbar)
    Toolbar mToolbarAlbum;
//
//
    @BindView(R.id.recyclerview_album_detail)
    RecyclerView mRecyclerViewSongAlbum;

    @BindView(R.id.image_abumdetail_back)
    ImageView mBackAlbumDetailImageView;


    @BindView(R.id.image_albumdetail_cover)
    ImageView mCoverAlbumDetailImageView;

    private static final String ALBUM_ID = "albumID";
    private static final String TAG = "AlbumDetailFragment";

    private List<Song> mSongList=new ArrayList<>();
    private AlbumDetailSongAdapter mSongListAdapter;

    private static final String FOLDER_SONG= "foldersong";

    private long mAlbumId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        hideMainActivtyUI();
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


    private  void init(){

        mAlbumId = getArguments().getLong(ALBUM_ID);

        mRecyclerViewSongAlbum.setLayoutManager(new LinearLayoutManager(getActivity()));
        mSongListAdapter = new AlbumDetailSongAdapter(mSongList,  getActivity());
        mRecyclerViewSongAlbum.setAdapter(mSongListAdapter);

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");


        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, mAlbumId);


        Glide.with(this)
                .load(abumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(mCoverAlbumDetailImageView);
        Log.d(TAG, "init: "+mAlbumId);
        if(mAlbumId!=0){
            new AlbumDetailPresenter(this).loadAlbumSongs(String.valueOf(mAlbumId));
        }else{
            List<Song> mSongList=(List<Song>) getArguments().getSerializable(FOLDER_SONG);
            mSongListAdapter.setmSongList(mSongList);
            mAppBarLayout.setVisibility(View.GONE);
            mToolabarLayout.setVisibility(View.VISIBLE);
        }
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


   @OnClick(R.id.image_abumdetail_back)
   public void onBackClicked(){
    Log.d(TAG, "onBackClicked: ");
       resetToolBarUI();
       getFragmentManager().popBackStackImmediate();
    }


    @OnClick(R.id.image_toolabar_back)
    public void onToolBarBackClicked(){
        Log.d(TAG, "onBackClicked: ");
        resetToolBarUI();
        ((MyApplication) getActivity().getApplicationContext())
                .bus()
                .send(new EventBus.sendDisableEnablePager(false));
        getFragmentManager().popBackStackImmediate();


    }


    @Override
    public void onSongsLoaded(List<Song> songs) {
        mSongListAdapter.setmSongList(songs);
        mToolbarAlbum.setTitle("");
    }
}

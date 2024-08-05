package com.music.musiqplayer.ui.albums;

import android.content.Context;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.AlbumListAdapter;
import com.music.musiqplayer.data.model.Album;
import com.music.musiqplayer.ui.albumdetails.AlbumDetailFragment;
import com.music.musiqplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class AlbumFragment extends BaseFragment implements IAlbumView, AlbumListAdapter.OnAlbumSelectedListener {


    @BindView(R.id.recycler_view_album)
    RecyclerView mRecyclerViewAlbum;

    private AlbumListAdapter mAlbumListAdapter;

    private FragmentManager mFragmentManager;
    private static final String TAG = "AlbumFragment";
    private static final String ALBUM_ID = "albumID";
    private List<Album> albumList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_album, container, false);

    }

    private void setUp() {
        mFragmentManager = getFragmentManager();
        mAlbumListAdapter = new AlbumListAdapter(null, getActivity());
        mRecyclerViewAlbum.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerViewAlbum.setAdapter(mAlbumListAdapter);
        mAlbumListAdapter.setOnAlbumSelectedListener(this);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }




    @Override
    public void onResume() {
        super.onResume();
        System.out.println("onResume fragment");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        Log.d(TAG, "onViewCreated: ");
        setUp();
        new AlbumListPresenter(this).onLoadAlbum();
        Disposable songListDispose = ((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.sendSelectedFragment) {
                                EventBus.sendSelectedFragment sendSelectedFragment = (EventBus.sendSelectedFragment) it;

                                if (sendSelectedFragment.getFragmentName().equals("album")) {
                                      if(albumList.size()==0){
                                          new AlbumListPresenter(this).onLoadAlbum();
                                      }
                                }
                            }
                        }
                );
        addDisposible(songListDispose);
    }


    @Override
    public void onAlbumLoaded(List<Album> songs) {
        albumList.clear();
        albumList=songs;
        mAlbumListAdapter.setmAlbumList(songs);
    }


    @Override
    public void onAlbumSelected(long albumid) {
        Log.d(TAG, "onAlbumSelected: " + albumid);
        Bundle arguments = new Bundle();
        arguments.putLong(ALBUM_ID, albumid);
        BaseFragment mAlbumDetailFragment = new AlbumDetailFragment();
        mAlbumDetailFragment.setArguments(arguments);
        String backToStackName = mAlbumDetailFragment.getClass().getName();

        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
        fragmentTransaction.addToBackStack(backToStackName);
        fragmentTransaction.replace(R.id.container_album, mAlbumDetailFragment);
        fragmentTransaction.commit();
    }


}

package com.music.musiqplayer.ui.artists;

import android.content.Context;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.ArtistAdapter;
import com.music.musiqplayer.data.model.Artist;
import com.music.musiqplayer.ui.artists.artistdetail.ArtistDetailFragment;
import com.music.musiqplayer.ui.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class ArtistFragment extends BaseFragment implements IArtistView,ArtistAdapter.OnArtistSelectedListener {

    @BindView(R.id.recycler_view_artist)
    RecyclerView mRecyclerViewArtist;
    private ArtistAdapter mArtistAdapter;
    private FragmentManager mFragmentManager;

    private List<Artist> artistList=new ArrayList<>();
    private static final String ALBUM_ID = "albumID";
    private static final String ARTIST_NAME = "artistName";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_artist, container, false);
    }

    // TODO: Rename method, update argument and hook method into UI event

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    private void setUp() {
        mFragmentManager = getFragmentManager();
        mArtistAdapter= new ArtistAdapter(null, getActivity());
        mRecyclerViewArtist.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        mRecyclerViewArtist.setAdapter(mArtistAdapter);
        mArtistAdapter.setOnArtistSelectedListener(this);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setUp();
        new ArtistPresenter(this).onArtistLoaded();
        Disposable songListDispose = ((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.sendSelectedFragment) {
                                EventBus.sendSelectedFragment sendSelectedFragment = (EventBus.sendSelectedFragment) it;

                                if (sendSelectedFragment.getFragmentName().equals("artist")) {
                                    if(artistList.size()==0){
                                        new ArtistPresenter(this).onArtistLoaded();
                                    }
                                }
                            }
                        }
                );
        addDisposible(songListDispose);

    }



    @Override
    public void onDetach() {
        super.onDetach();

    }


    @Override
    public void onArtistLoaded(List<Artist> songs) {
      mArtistAdapter.setArtistList(songs);
      artistList.clear();
      artistList.addAll(songs);

    }

    @Override
    public void onArtistClicked(Artist artist) {
        Bundle arguments = new Bundle();
        arguments.putLong(ALBUM_ID,artist.albumID);
        arguments.putString(ARTIST_NAME,artist.artist);
        BaseFragment mArtistDetailFragment = new ArtistDetailFragment();
        mArtistDetailFragment.setArguments(arguments);
        String backToStackName = mArtistDetailFragment.getClass().getName();
        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction()/*.addToBackStack("")*/;
        fragmentTransaction.addToBackStack(backToStackName);
        fragmentTransaction.replace(R.id.container_artist,mArtistDetailFragment);
        fragmentTransaction.commit();
    }
}

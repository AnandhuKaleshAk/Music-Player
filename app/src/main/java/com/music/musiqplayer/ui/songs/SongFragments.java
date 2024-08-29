package com.music.musiqplayer.ui.songs;

import android.content.Context;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.R;
import com.music.musiqplayer.adapter.SongListAdapter;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.base.BaseFragment;
import com.music.musiqplayer.ui.main.MainActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class SongFragments extends BaseFragment implements ISongsListView {


    @BindView(R.id.recycler_view_songList)
    RecyclerView mRecyclerViewSong;

    private static final String TAG = "SongFragments";

    private SongListAdapter mSongListAdapter;
    private static final String FOLDER_SONG= "foldersong";

    private List<Song> mSongList;

    private InterstitialAd mInterstitialAd;

    private final String adUnitId = "ca-app-pub-1159766989226286/6039771769"; // Replace with your real Ad Unit ID


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_songs, container, false);
    }

    private void setDefaults() {
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getActivity());
        mRecyclerViewSong.setLayoutManager(linearLayoutManager);
        mSongListAdapter = new SongListAdapter(null, getActivity());
        mRecyclerViewSong.setAdapter(mSongListAdapter);

        Disposable songListDispose = ((MyApplication) getActivity().getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.UpdateSongList) {
                                EventBus.UpdateSongList sendSongList = (EventBus.UpdateSongList) it;
                                mSongList = sendSongList.getmSongList();
                                Log.d(TAG, "onLocalMusicLoaded: "+sendSongList.getmSongList().size());
                                mSongListAdapter.setmSongList(mSongList);
                                 showInterstitialAd();
                            }
                        }
                );
        addDisposible(songListDispose);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this, view);
        setDefaults();
        new SongsListPresenter(this).loadLocalMusic();
        loadInterstitialAd();

    }



    private void loadInterstitialAd() {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(requireContext(), adUnitId, adRequest, new InterstitialAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                mInterstitialAd = interstitialAd;
                // Set the full-screen content callback
                mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                    @Override
                    public void onAdDismissedFullScreenContent() {
                        // Code to be executed when the interstitial ad is dismissed.
                        mInterstitialAd = null; // Load another ad
                        loadInterstitialAd();
                    }

                    @Override
                    public void onAdFailedToShowFullScreenContent(AdError adError) {
                        // Code to be executed when the ad failed to display.
                        mInterstitialAd = null;
                    }

                    @Override
                    public void onAdShowedFullScreenContent() {
                        // Code to be executed when the ad is shown.
                        mInterstitialAd = null;
                    }
                });
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                // Handle the error
                mInterstitialAd = null;
            }
        });
    }

    private void showInterstitialAd() {
        if (mInterstitialAd != null) {
            mInterstitialAd.show(requireActivity());
        } else {
            // Ad wasn't ready; load another ad
            loadInterstitialAd();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void emptyView(boolean visible) {

    }

    @Override
    public void handleError(Throwable error) {

    }

    @Override
    public void onLocalMusicLoaded(List<Song> songs) {
//        Log.d(TAG, "onLocalMusicLoaded: "+songs.size());
//        mSongListAdapter.setmSongList(songs);


    }




}

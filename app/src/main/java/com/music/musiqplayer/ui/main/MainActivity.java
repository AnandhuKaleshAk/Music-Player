package com.music.musiqplayer.ui.main;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;

import android.os.IBinder;
import android.os.PersistableBundle;
import android.provider.Settings;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;


import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.music.musiqplayer.AppUtils;
import com.music.musiqplayer.EventBus;
import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.PlaybackStatus;
import com.music.musiqplayer.PlayerService;
import com.music.musiqplayer.adapter.SongPagerAdapter;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.ui.albums.AlbumMainFragment;
import com.music.musiqplayer.ui.artists.ArtistMainFragment;
import com.music.musiqplayer.ui.base.BaseFragment;
import com.music.musiqplayer.ui.equalizer.EqualizerActivity;
import com.music.musiqplayer.ui.folder.FolderMainFragment;
import com.music.musiqplayer.ui.genres.GenresMainFragment;
import com.music.musiqplayer.ui.playlist.PlayListMainFragment;
import com.music.musiqplayer.ui.search.SearchSongActivity;
import com.music.musiqplayer.ui.sleeptimer.SleepTimerActivity;
import com.music.musiqplayer.ui.songs.SongFragments;
import com.google.android.material.navigation.NavigationView;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager.widget.ViewPager;

import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.music.musiqplayer.R;
import com.music.musiqplayer.ui.base.BaseActivity;
import com.google.android.material.tabs.TabLayout;
import com.music.musiqplayer.utils.CustomViewPager;
import com.sothree.slidinguppanel.SlidingUpPanelLayout;

import java.io.Serializable;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_MEDIA_AUDIO;
import static android.Manifest.permission.READ_PHONE_STATE;

public class MainActivity extends BaseActivity implements IMainMvpView, ViewPager.OnPageChangeListener {

    public MainPresenter mPresenter;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.drawer_layout)
    DrawerLayout mDrawerLayout;

    @BindView(R.id.tabs)
    TabLayout mTabLayout;

    @BindView(R.id.nav_view)
    NavigationView mNavigationView;

    @BindView(R.id.main_viewPager)
    CustomViewPager mViewPager;

    @BindView(R.id.now_playing_viewPager)
    ViewPager mPlayingViewPager;

    @BindView(R.id.slidingRelativeLayout)
    RelativeLayout mSlidingRelativeLayout;

    @BindView(R.id.text_name_itemPlay)
    TextView mSongNameItemTextView;

    @BindView(R.id.text_artist_itemPlay)
    TextView mSongArtistItemTextView;


    @BindView(R.id.text_start_itemPlay)
    TextView mSongTimeItemTextView;


    @BindView(R.id.text_end_itemPlay)
    TextView mSongEndItemTextView;


    TextView mSongTimerTextView;

    TextView mSongNamNavTextView;

    TextView mArtistNavextView;

    ImageView mNavigationDrawerImageView;

    private int lastPosition = 0;

    @BindView(R.id.image_play_itemPlay)
    ImageView mSongPlayPauseItemImageView;


    @BindView(R.id.image_repeat_itemPlay)
    ImageView mSongRepeatItemImageView;


    @BindView(R.id.image_shuffle_itemPlay)
    ImageView mSongShuffleItemImageView;


    @BindView(R.id.image_favourite_itemPlay)
    ImageView mSongFavouriteImageView;

    private Song mSong;

    List<Song> mSongList;

    private PlayerService mPlayerService;

    private FragmentManager mFragmentManager;

    private SongPagerAdapter mSongPagerAdapter;

    private ActionBarDrawerToggle mDrawerToggle;
    private SlidingUpPanelLayout mSlideLayout;
    private String mTitles[];

    private static final String TAG = "MainActivity";
    public static final String SONGS = "songs";
    public static final String POSITION = "position";
    private AppUtils mAppUtils;
    private int mPostion = 0;

    boolean serviceBound = false;

    private Context mContext = this;

    @BindView(R.id.seekBar_music)
    SeekBar mSeekBarMusic;

    public static final String BROADCAST_PLAYER = "song";
    public static Boolean isDragged = false;

    private static final int PERMISSION = 101;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        try {
            checkPermission();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        init();

    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState,
                                    @NonNull PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
    }


    private void init() {
        mAppUtils = new AppUtils(this);
        View headerView = mNavigationView.getHeaderView(0);
        mSongTimerTextView = headerView.findViewById(R.id.timerNavTextview);
        mNavigationDrawerImageView = headerView.findViewById(R.id.navDrawerImageView);
        mNavigationDrawerImageView.setImageResource(R.drawable.ic_music_image);
        mSongNamNavTextView = headerView.findViewById(R.id.name_nav_textview);
        mSongArtistItemTextView = headerView.findViewById(R.id.artist_nav_textview);

        ColorStateList csl = new ColorStateList(
                new int[][]{
                        new int[]{-android.R.attr.state_checked}, // unchecked
                        new int[]{android.R.attr.state_checked}  // checked
                },
                new int[]{
                        Color.WHITE,
                        Color.RED
                }
        );
        mNavigationView.setItemTextColor(csl);
        mNavigationView.setItemIconTintList(csl);

        mAppUtils.setTimerOnOFf(false);

    }


    private void initialiseAd() {
        new Thread(
                () -> {
                    // Initialize the Google Mobile Ads SDK on a background thread.
                    MobileAds.initialize(this, initializationStatus -> {
                    });
                })
                .start();

    }

    private void checkPermission() throws InterruptedException {
        if (!checkingPermissionIsEnabledOrNot()) {
            showPermissionPopUp();
        } else {
            setUP();
            events();
            evenListeners();
            initialiseAd();

        }

    }

    public void showPermissionPopUp() {
        LayoutInflater layoutInflaterAndroid = LayoutInflater.from(this);
        View mView = layoutInflaterAndroid.inflate(R.layout.user_input_dialog_box, null);
        AlertDialog.Builder alertDialogBuilderUserInput = new AlertDialog.Builder(this);
        alertDialogBuilderUserInput.setView(mView);
        final Button allowPermissionButton = mView.findViewById(R.id.allow_permission_button);

        AlertDialog alertDialogAndroid = alertDialogBuilderUserInput.create();
        alertDialogAndroid.show();

        allowPermissionButton.setOnClickListener(v -> {
            requestMultiplePermission();
            alertDialogAndroid.cancel();
        });

    }

    //Permission function starts from here
    private void requestMultiplePermission() {

        // Creating String Array with Permissions.
        ActivityCompat.requestPermissions(MainActivity.this, new String[]
                {
                        READ_PHONE_STATE,
                        READ_MEDIA_AUDIO,

                }, PERMISSION);

    }

    // Calling override method.
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {

            case PERMISSION:

                if (grantResults.length > 0) {

                    boolean phonePermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storagePermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    if (phonePermission && storagePermission) {
                        setUP();
                        events();
                        evenListeners();
                    } else {
                        Toast.makeText(MainActivity.this, getString(R.string.text_permission_denied), Toast.LENGTH_LONG).show();
                        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                        Uri uri = Uri.fromParts("package", getPackageName(), null);
                        intent.setData(uri);
                        startActivity(intent);
                        finish();
                    }
                }

                break;
        }
    }

    public boolean checkingPermissionIsEnabledOrNot() {
        int phonePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_PHONE_STATE);
        int storagePermissionResult = ContextCompat.checkSelfPermission(getApplicationContext(), READ_MEDIA_AUDIO);

        return phonePermissionResult == PackageManager.PERMISSION_GRANTED &&
                storagePermissionResult == PackageManager.PERMISSION_GRANTED;

    }

    private void evenListeners() {

    }


    private void events() {

        Disposable songListDispose = ((MyApplication) getApplication())
                .bus()
                .toObservable().subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        it -> {
                            if (it instanceof EventBus.SendSongList) {
                                EventBus.SendSongList sendSongList = (EventBus.SendSongList) it;
                                mSongList = sendSongList.getmSongList();
                                playAudio(mSongList, sendSongList.getmPosition());
                                mSongPagerAdapter = new SongPagerAdapter(this, sendSongList.getmSongList());
                                mPlayingViewPager.setAdapter(mSongPagerAdapter);
                                mPlayingViewPager.setCurrentItem(sendSongList.getmPosition(), true);

                                mSong = mSongList.get(mPlayingViewPager.getCurrentItem());

                                mPostion = sendSongList.getmPosition();
                                setmNavigationView(mSongList.get(mPostion));
//                                if (sendSongList.getmPosition() == 0) {
//                                    mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.HIDDEN);
//                                } else
//                                    mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
//
//                                }
                                setmNavigationView(mSongList.get(mPostion));
                                mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);

                            }

                            if (it instanceof EventBus.SendSongService) {
                                EventBus.SendSongService sendSongList = (EventBus.SendSongService) it;
                                mSongPagerAdapter = new SongPagerAdapter(this, sendSongList.getmSongList());
                                mPostion = sendSongList.getmPosition();
                                mPlayingViewPager.setAdapter(mSongPagerAdapter);
                                mPlayingViewPager.setCurrentItem(sendSongList.getmPosition(), true);
                                if (PlayerService.isPlaying) {
                                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
                                } else {
                                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_play_large);
                                }
                                setmNavigationView(mSongList.get(mPostion));
                            }
                            if (it instanceof EventBus.sendProgress) {
                                EventBus.sendProgress sendProgress = (EventBus.sendProgress) it;
                                setSeebarProgress(sendProgress.getDuration(), sendProgress.getCurrentDuration(), sendProgress.getmPlaybackStatus());
                            }
                            if (it instanceof EventBus.hideUIActivity) {
                                EventBus.hideUIActivity status = (EventBus.hideUIActivity) it;
                                if (status.getStatus()) {
                                    hideToolBarUI();
                                } else {
                                    resetToolBarUI();
                                }
                            }


                            if (it instanceof EventBus.PlayPause) {
                                EventBus.PlayPause playPause = (EventBus.PlayPause) it;
                                if (PlayerService.isPlaying) {
                                    mPlayerService.pauseMedia();
                                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_play_large);
                                } else {
                                    mPlayerService.resumeMedia();
                                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
                                }
                                setPanelDragged();
                            }
                            if (it instanceof EventBus.SendTimer) {
                                EventBus.SendTimer eventSendTimer = (EventBus.SendTimer) it;
                                Log.d(TAG, "events: " + eventSendTimer.getTimerSeconds());
                                setSleepTimer(eventSendTimer.getTimerSeconds());
                            }
                            if (it instanceof EventBus.SendDeleteList) {
                                EventBus.SendDeleteList sendDeleteList = (EventBus.SendDeleteList) it;
                                mSongList = sendDeleteList.getmSongList();
                                //   playAudio(mSongList, sendDeleteList.getmPosition());

                                mSongPagerAdapter.setmSongList(mSongList);
//                      //         playAudio(mSongList, sendDeleteList.getmPosition());
//                                mSongPagerAdapter = new SongPagerAdapter(this, sendDeleteList.getmSongList());
//                                mPlayingViewPager.setAdapter(mSongPagerAdapter);
//                                mSongPagerAdapter.notifyDataSetChanged();
//                                //        mPlayingViewPager.setCurrentItem(PlayerService.currentSongPosition, true);

                                mPostion = PlayerService.currentSongPosition;
//                                if (sendSongList.getmPosition() == 0) {
                            }
                            if (it instanceof EventBus.SlideLayout) {
                                EventBus.SlideLayout isSlideLayout = (EventBus.SlideLayout) it;
                                if (isSlideLayout.isiSlideLayout()) {
                                    if (mSlideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                                        mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                                    }
                                }

                            }
                            if(it instanceof EventBus.sendDisableEnablePager){
                                EventBus.sendDisableEnablePager isEnabledDisable=(EventBus.sendDisableEnablePager)it;
                                Log.d(TAG, "enableDisable" + isEnabledDisable);
                                if(isEnabledDisable.getDisablePlayList()){
                                    mViewPager.disableScroll(true);
                                }else{
                                    mViewPager.disableScroll(false);
                                }
                            }

                        }
                );

        addDisposible(songListDispose);
    }


    @SuppressLint("DefaultLocale")
    private void setSleepTimer(long timeInMilliSeconds) {

        if (timeInMilliSeconds != 0) {
            mSongTimerTextView.setVisibility(View.VISIBLE);
        } else {
            mSongTimerTextView.setVisibility(View.GONE);
        }
        long totalSeconds = timeInMilliSeconds / 1000;
        long minutes = totalSeconds / 60;
        long hours = minutes / 60;
        minutes = minutes - (hours * 60);
        totalSeconds = totalSeconds % 60;
        String time;
        if (hours < 1) {
            time = String.format("%02d:%02d", minutes, totalSeconds);
        } else {
            time = String.format("%02d:%02d:%02d", hours, minutes, totalSeconds);
        }
        mSongTimerTextView.setText(time);

    }


    @SuppressLint("DefaultLocale")
    private void setSeebarProgress(int duration, int currentDuration, PlaybackStatus getmPlaybackStatus) {
        mSeekBarMusic.setMax(duration);
        mSeekBarMusic.setProgress(currentDuration);

        int secTimeCurrent = Integer.parseInt(String.valueOf(currentDuration / 1000));
        int minuteCurrent = secTimeCurrent / 60;
        secTimeCurrent = secTimeCurrent % 60;


        mSongTimeItemTextView.setText(String.format("%02d:%02d", minuteCurrent, secTimeCurrent));

        int secTimeduration = Integer.parseInt(String.valueOf(duration / 1000));
        int minuteDuration = secTimeduration / 60;
        secTimeduration = secTimeduration % 60;


        mSongEndItemTextView.setText(String.format("%02d:%02d", minuteDuration, secTimeduration));


    }


    @OnClick(R.id.image_play_itemPlay)
    public void onClickPlayPause() {
        if (PlayerService.isPlaying) {
            mPlayerService.pauseMedia();
            mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_play_large);
        } else {
            mPlayerService.resumeMedia();
            mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
        }

    }


    @OnClick(R.id.image_next_itemPlay)
    void onClickNext() {
        // mPlayingViewPager.setCurrentItem(mPlayingViewPager.getCurrentItem() + 1);
        mPlayerService.skipToNext();
        mPlayerService.sendSongsList();

        mPlayerService.updateMetaData();
        mPlayerService.buildNotification(PlaybackStatus.PLAYING);

    }

    @OnClick(R.id.image_prev_itemPlay)
    void onClickPrev() {
//        mPlayingViewPager.setCurrentItem(mPlayingViewPager.getCurrentItem() - 1);

        mPlayerService.skipToPrevious();
        mPlayerService.sendSongsList();
        mPlayerService.updateMetaData();
        mPlayerService.buildNotification(PlaybackStatus.PLAYING);

    }

    @OnClick(R.id.image_favourite_itemPlay)
    void favorites() {
        mPresenter.onFavoriteClicked(mSong);
    }

    @OnClick(R.id.search_home)
    void onSearchViewClicked() {
        Log.d(TAG, "onSearchViewClicked: ");
        startActivity(new Intent(this, SearchSongActivity.class));
    }


    @OnClick(R.id.image_repeat_itemPlay)
    void onClickRepeat() {
        Log.d(TAG, "onClickRepeat: " + PlayerService.isRepeat);
        if (PlayerService.isRepeat) {
            mSongRepeatItemImageView.setImageResource(R.drawable.ic_repeat);
            PlayerService.isRepeat = false;

        } else {
            mSongRepeatItemImageView.setImageResource(R.drawable.ic_repeat_selected);
            PlayerService.isRepeat = true;
            if (PlayerService.isShuffle) {
                mSongShuffleItemImageView.setImageResource(R.drawable.ic_shuffle);
                PlayerService.isShuffle = false;
            }

        }
        Log.d(TAG, "onClickRepeat: repeat " + PlayerService.isRepeat);
        Log.d(TAG, "onClickRepeat: shuffle " + PlayerService.isShuffle);


//
    }

    public void setmNavigationView(Song song){

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");


        Uri albumArturi = ContentUris.withAppendedId(sArtworkUri, song.getAlbumID());


        Glide.with(mContext)
                .load(albumArturi.toString())
                .placeholder(R.drawable.ic_music_image)
                .into(mNavigationDrawerImageView);

        mSongNamNavTextView.setText(song.getTitle());
        mSongArtistItemTextView.setText(song.getArtist());

    }

    @OnClick(R.id.image_shuffle_itemPlay)
    void onClickShuffle() {
        if (PlayerService.isShuffle) {
            mSongShuffleItemImageView.setImageResource(R.drawable.ic_shuffle);
            PlayerService.isShuffle = false;

        } else {
            mSongShuffleItemImageView.setImageResource(R.drawable.ic_shuffle_selected);
            if (PlayerService.isRepeat) {
                mSongRepeatItemImageView.setImageResource(R.drawable.ic_repeat);
                PlayerService.isRepeat = false;
            }
            PlayerService.isShuffle = true;
        }
        Log.d(TAG, "onClickShuffle: repeat " + PlayerService.isRepeat);
        Log.d(TAG, "onClickShuffle: shuffle" + PlayerService.isShuffle);

    }


    @Override
    public void showLoading() {
        super.showLoading();
    }

    @Override
    public Context getContext() {
        return null;
    }

    public void setUP() {
        mAppUtils = new AppUtils(this);
        mPresenter = new MainPresenter(this, MainActivity.this);
        mSlideLayout = findViewById(R.id.sliding_layout);
        setSupportActionBar(mToolbar);
        setSupportActionBar(mToolbar);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,
                mDrawerLayout,
                mToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                hideKeyboard();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.syncState();
        setupNavMenu();
        mTitles = getResources().getStringArray(R.array.fragment_titles);

        BaseFragment fragments[] = new BaseFragment[mTitles.length];
        fragments[0] = new SongFragments();
        fragments[1] = new AlbumMainFragment();
        fragments[2] = new FolderMainFragment();
        fragments[3] = new PlayListMainFragment();
        fragments[4] = new ArtistMainFragment();
        fragments[5] = new GenresMainFragment();

        for (String k : mTitles) {
            mTabLayout.addTab(mTabLayout.newTab().setText(k));
        }

        MainPagerAdapter mainPagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), mTitles, fragments);
        mViewPager.setAdapter(mainPagerAdapter);

        mViewPager.setOffscreenPageLimit(5);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout));
        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                mViewPager.setCurrentItem(tab.getPosition());

                if (tab.getPosition() == 1) {
                    ((MyApplication) mContext.getApplicationContext())
                            .bus()
                            .send(new EventBus.sendSelectedFragment(tab.getPosition(),true,"album"));

                }


                if (tab.getPosition() == 4) {
                    ((MyApplication) mContext.getApplicationContext())
                            .bus()
                            .send(new EventBus.sendSelectedFragment(tab.getPosition(),true,"artist"));
                }
                if(tab.getPosition()==5){
                    ((MyApplication) mContext.getApplicationContext())
                            .bus()
                            .send(new EventBus.sendSelectedFragment(tab.getPosition(),true,"genre"));
                }


//
//
//                getSupportFragmentManager().popBackStackImmediate(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                resetToolBarUI();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        mPlayingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                Log.d(TAG, "data: "+position);
            }

            @Override
            public void onPageSelected(int position) {



                PlayerService.currentSongPosition=position;
                mSongNameItemTextView.setText(mSongList.get(position).getDisplayName());
                mSongArtistItemTextView.setText(mSongList.get(position).getArtist());

                System.out.println("artist"+mSongArtistItemTextView.getText().toString());
                mPostion = position;
                if (PlayerService.isPlaying) {
                    mPresenter.onSavePlayedTime(mSongList.get(position));
                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
                } else {
                    mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_play_large);
                }

                setFavorite();


            }

            @Override
            public void onPageScrollStateChanged(int state) {
                System.out.println("state"+state);
                if (state == 2) {
                    if(mPlayingViewPager.getCurrentItem()>PlayerService.currentSongPosition){
                        mPlayerService.skipToNext();
                    }else{
                        mPlayerService.skipToPrevious();
                    }
                    mPlayerService.sendSongsList();
                    mPlayerService.updateMetaData();
                    mPlayerService.buildNotification(PlaybackStatus.PLAYING);

                }
            }

        });


        mSlideLayout.addPanelSlideListener(new SlidingUpPanelLayout.PanelSlideListener() {
            @Override
            public void onPanelSlide(View panel, float slideOffset) {

            }

            @Override
            public void onPanelStateChanged(View panel, SlidingUpPanelLayout.PanelState previousState, SlidingUpPanelLayout.PanelState newState) {

                if (previousState == SlidingUpPanelLayout.PanelState.COLLAPSED) {
                    isDragged = true;
                    setPanelDragged();

                } else if (previousState == SlidingUpPanelLayout.PanelState.EXPANDED) {
                    isDragged = false;
                    setPanelDragged();

                }


            }
        });

        mSlidingRelativeLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mPlayerService, "sliding", Toast.LENGTH_SHORT).show();
            }
        });

        mSeekBarMusic.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mPlayerService.seekToPosition(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });

        Display display = getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);


        ViewGroup.LayoutParams params = mPlayingViewPager.getLayoutParams();
        int height = (int) (outMetrics.heightPixels / 1.8);
        params.height = height;
        mPlayingViewPager.setLayoutParams(params);


    }


    private void setFavorite() {
        mSong = mSongList.get(mPlayingViewPager.getCurrentItem());
        mPresenter.getFavouriteSongs(mSong);
    }

    public void setPanelDragged() {
        mSongPagerAdapter = new SongPagerAdapter(this, mSongList);
        mPlayingViewPager.setAdapter(mSongPagerAdapter);
        mPlayingViewPager.setCurrentItem(mPostion, true);

        mSongNameItemTextView.setText(mSongList.get(mPostion).getDisplayName());
        mSongArtistItemTextView.setText(mSongList.get(mPostion).getArtist());

        if (!PlayerService.isPlaying) {
            mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_play_large);
        } else {
            mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
        }

    }

    private ServiceConnection playerServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            PlayerService.LocalBinder binder = (PlayerService.LocalBinder) service;
            mPlayerService = binder.getService();
            serviceBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            serviceBound = false;
        }
    };


    private void playAudio(List<Song> songs, int position) {
        mPresenter.onSavePlayedTime(songs.get(position));
        if (!serviceBound) {
            PlayerService.isPlaying = true;

            mSongPlayPauseItemImageView.setImageResource(R.drawable.ic_pause_button);
            mAppUtils.storeAudio(songs);
            mAppUtils.storeAudioIndex(position);
            Intent playerIntent = new Intent(this, PlayerService.class);
            playerIntent.putExtra(SONGS, (Serializable) songs);
            playerIntent.putExtra(POSITION, position);
            startService(playerIntent);
            bindService(playerIntent, playerServiceConnection, Context.BIND_AUTO_CREATE);


        } else {
            PlayerService.isPlaying = true;
            //Service is active
            //Send a broadcast to the service -> PLAY_NEW_AUDIO
            Intent broadcastIntent = new Intent(BROADCAST_PLAYER);
            mAppUtils.storeAudio(songs);
            mAppUtils.storeAudioIndex(position);
            broadcastIntent.putExtra(SONGS, (Serializable) songs);
            broadcastIntent.putExtra(POSITION, position);
            sendBroadcast(broadcastIntent);

        }

//        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);
    }


    void setupNavMenu() {
        View headerLayout = mNavigationView.getHeaderView(0);

        mNavigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                        mDrawerLayout.closeDrawer(GravityCompat.START);
                        switch (item.getItemId()) {
                            case R.id.nav_home:
                                mPresenter.onDrawerHomeClicked();
//                                return true;
//                            case R.id.nav_equalizer:
//                                mPresenter.onDrawerEquilizerClicked();
//                                return true;
//                            case R.id.nav_settings:
//                                mPresenter.onDrawerSettingsClicked();
                                return true;
                            case R.id.nav_timer:
                                mPresenter.onDrawerTimerClicked();
                                return true;
                            case R.id.nav_share:
                                mPresenter.onDrawerShareAppClicked();
                                return true;
                            default:
                                return false;
                        }
                    }
                });


    }


    @Override
    protected void onResume() {
        super.onResume();


    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        savedInstanceState.putBoolean(getResources().getString(R.string.text_saved_state), serviceBound);
        super.onSaveInstanceState(savedInstanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        serviceBound = savedInstanceState.getBoolean(getResources().getString(R.string.text_saved_state));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (serviceBound) {
            unbindService(playerServiceConnection);
            mPlayerService.stopSelf();
        }
    }


    @Override
    public void onBackPressed() {
        mViewPager.disableScroll(false);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        if (mSlideLayout.getPanelState() == SlidingUpPanelLayout.PanelState.EXPANDED) {
            mSlideLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);
        } else {
            resetToolBarUI();
            int fragmentsInStack = getSupportFragmentManager().getBackStackEntryCount();
            if (fragmentsInStack > 1) { // If we have more than one fragment, pop back stack
                getSupportFragmentManager().popBackStackImmediate();
            } else if (fragmentsInStack == 1) { // Finish activity, if only one fragment left, to prevent leaving empty screen
            //    finish();
            } else {
              //  super.onBackPressed();
            }
        }


        //                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            super.onBackPressed();


//        if (mViewPager.getCurrentItem() != 0) {
//            mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1, true);
//        } else {

      //  }


    }

    public void resetToolBarUI() {
        mTabLayout.setVisibility(View.VISIBLE);
        mToolbar.setVisibility(View.VISIBLE);
    }

    public void hideToolBarUI() {
        mTabLayout.setVisibility(View.GONE);
        mToolbar.setVisibility(View.GONE);
    }


    @Override
    public void onFavouriteLoaded(Song song) {
        if (song.isFavorite()) {
            mSongFavouriteImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorRed), android.graphics.PorterDuff.Mode.SRC_IN);
        } else {
            mSongFavouriteImageView.setColorFilter(ContextCompat.getColor(this, R.color.colorLightWhite), android.graphics.PorterDuff.Mode.SRC_IN);
        }
        mSong = song;


//        ((MyApplication) getApplicationContext())
//                .bus()
//                .send(new EventBus.LoadFavourites(true));
    }

    @Override
    public void showHomeFragment() {

    }

    @Override
    public void showEqualizerFragment() {
        startActivity(new Intent(this, EqualizerActivity.class));
    }

    @Override
    public void showSleepFragment() {
        startActivity(new Intent(this, SleepTimerActivity.class));
    }

    @Override
    public void showSettingsFragment() {
    }

    @Override
    public void showShareAppFragment() {

    }


    @Override
    public void closeNavigationDrawer() {

    }

    @Override
    public void lockDrawer() {

    }

    @Override
    public void unlockDrawer() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {


    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


}

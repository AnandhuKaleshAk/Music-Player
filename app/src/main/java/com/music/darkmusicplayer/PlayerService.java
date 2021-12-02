package com.music.darkmusicplayer;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.session.MediaSessionManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;


import android.provider.MediaStore;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.ui.main.MainActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import static com.music.darkmusicplayer.ui.main.MainActivity.SONGS;

public class PlayerService extends Service implements MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, MediaPlayer.OnSeekCompleteListener,
        MediaPlayer.OnInfoListener, MediaPlayer.OnBufferingUpdateListener,
        AudioManager.OnAudioFocusChangeListener {

    public static final String ACTION_PLAY = "com.music.darkmusicplayer.ACTION_PLAY";
    public static final String ACTION_PAUSE = "com.music.darkmusicplayer.ACTION_PAUSE";
    public static final String ACTION_PREVIOUS = "com.music.darkmusicplayer.ACTION_PREVIOUS";
    public static final String ACTION_NEXT = "com.music.darkmusicplayer.ACTION_NEXT";
    public static final String ACTION_STOP = "com.music.darkmusicplayer.ACTION_STOP";
    private static final String TAG = "PlayerService";

    private AudioManager audioManager;

    //Handle incoming phone calls

    private boolean ongoingCall = false;
    private PhoneStateListener phoneStateListener;
    private TelephonyManager telephonyManager;


    public static MediaPlayer mediaPlayer;
    private MediaSessionManager mediaSessionManager;
    private MediaSessionCompat mediaSession;
    private MediaControllerCompat.TransportControls transportControls;
    private static final int NOTIFICATION_ID = 101;

    private static List<Song> songList;
    private Song song;

    private int resumePosition;
    public static int currentSongPosition;


    private final IBinder iBinder = new LocalBinder();

    public static final String MEDIA = "media";

    private AppUtils mAppUtils;
    private Intent intentData;
    NotificationManager notificationManager;

    public static boolean isPlaying = false;
    public static boolean isShuffle = false;
    public static boolean isRepeat = false;


    @Override
    public void onCreate() {
        super.onCreate();
        // Perform one-time setup procedures

        callStateListener();
        //ACTION_AUDIO_BECOMING_NOISY -- change in audio outputs -- BroadcastReceiver
        // registerBecomingNoisyReceiver();
        //Listen for new Audio to play -- BroadcastReceiver
        registerAudioIntent();
    }

    private void registerAudioIntent() {
        //Register playNewMedia receiver
        IntentFilter filter = new IntentFilter(MainActivity.BROADCAST_PLAYER);
        registerReceiver(mSongReceiver, filter);
    }

    private void registerBecomingNoisyReceiver() {
        //register after getting audio focus
        IntentFilter intentFilter = new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY);
        registerReceiver(becomingNoisyReceiver, intentFilter);


    }


    private BroadcastReceiver becomingNoisyReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            //pause audio on ACTION_AUDIO_BECOMING_NOISY
            pauseMedia();

        }
    };

    private BroadcastReceiver mSongReceiver = new BroadcastReceiver() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void onReceive(Context context, Intent intent) {
            songList = (List<Song>) intent.getSerializableExtra(SONGS);
            currentSongPosition = intent.getIntExtra(MainActivity.POSITION, 0);


            mAppUtils = new AppUtils(getApplicationContext());
            mAppUtils.storeAudioIndex(currentSongPosition);


            Log.d(TAG, "onReceive: " + mAppUtils.loadAudioIndex());
            stopMedia();
            mediaPlayer.reset();
            initMediaPlayer();
            updateMetaData();
            buildNotification(PlaybackStatus.PLAYING);

        }
    };


    private void callStateListener() {
        // Get the telephony manager
        telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        //Starting listening for PhoneState changes
        phoneStateListener = new PhoneStateListener() {
            @Override
            public void onCallStateChanged(int state, String incomingNumber) {
                switch (state) {
                    //if at least one call exists or the phone is ringing
                    //pause the MediaPlayer
                    case TelephonyManager.CALL_STATE_OFFHOOK:
                    case TelephonyManager.CALL_STATE_RINGING:
                        if (mediaPlayer != null) {
                            pauseMedia();
                            ongoingCall = true;
                        }
                        break;
                    case TelephonyManager.CALL_STATE_IDLE:
                        // Phone idle. Start playing.
                        if (mediaPlayer != null) {
                            if (ongoingCall) {
                                ongoingCall = false;
                                resumeMedia();
                            }
                        }
                        break;
                }
            }
        };
        // Register the listener with the telephony manager
        // Listen for changes to the device call state.
        telephonyManager.listen(phoneStateListener,
                PhoneStateListener.LISTEN_CALL_STATE);
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return iBinder;
    }


    //The system calls this method when an activity, requests the service be started
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        try {
            //An audio file is passed to the service through putExtra();
            songList = (ArrayList<Song>) intent.getSerializableExtra(SONGS);
            currentSongPosition = intent.getIntExtra(MainActivity.POSITION, 0);
            mAppUtils = new AppUtils(getApplicationContext());
            Log.d(TAG, "onStartCommand: " + songList.size());
            Log.d(TAG, "onStartCommand: position" + currentSongPosition);

        } catch (NullPointerException e) {
            stopSelf();
        }

        //Request audio focus
        if (requestAudioFocus() == false) {
            //Could not gain focus
            stopSelf();
        }

        if (songList != null) {
            if (mediaSessionManager == null) {
                try {
                    initMediaSession();
                    initMediaPlayer();
                } catch (RemoteException e) {
                    e.printStackTrace();
                    stopSelf();
                }
                buildNotification(PlaybackStatus.PLAYING);
            }


        }
        Log.d(TAG, "onStartCommand: " + intent);
        handleIncomingActions(intent);
        return super.onStartCommand(intent, flags, startId);
    }

    private void handleIncomingActions(Intent playbackAction) {
        if (playbackAction == null || playbackAction.getAction() == null) return;

        String actionString = playbackAction.getAction();
        if (actionString.equalsIgnoreCase(ACTION_PLAY)) {
            transportControls.play();
        } else if (actionString.equalsIgnoreCase(ACTION_PAUSE)) {
            transportControls.pause();
        } else if (actionString.equalsIgnoreCase(ACTION_NEXT)) {
            transportControls.skipToNext();
        } else if (actionString.equalsIgnoreCase(ACTION_PREVIOUS)) {
            transportControls.skipToPrevious();
        } else if (actionString.equalsIgnoreCase(ACTION_STOP)) {
            transportControls.stop();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void initMediaSession() throws RemoteException {
        if (mediaSessionManager != null) return; //mediaSessionManager exists

        mediaSessionManager = (MediaSessionManager) getSystemService(Context.MEDIA_SESSION_SERVICE);
        // Create a new MediaSession
        mediaSession = new MediaSessionCompat(getApplicationContext(), "AudioPlayer");
        //Get MediaSessions transport controls
        transportControls = mediaSession.getController().getTransportControls();
        //set MediaSession -> ready to receive media commands
        mediaSession.setActive(true);
        //indicate that the MediaSession handles transport control commands
        // through its MediaSessionCompat.Callback.
        mediaSession.setFlags(MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        Log.d(TAG, "initMediaSession: ");
        updateMetaData();
        mediaSession.setCallback(new MediaSessionCompat.Callback() {
            @Override
            public void onPlay() {
                super.onPlay();
                resumeMedia();
                sendSongsList();
            }

            @Override
            public void onPause() {
                super.onPause();
                pauseMedia();

                sendSongsList();
            }

            @Override
            public void onSkipToNext() {
                super.onSkipToNext();
                skipToNext();
                sendSongsList();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onSkipToPrevious() {
                super.onSkipToPrevious();
                skipToPrevious();
                sendSongsList();
                updateMetaData();
                buildNotification(PlaybackStatus.PLAYING);
            }

            @Override
            public void onStop() {
                super.onStop();
                removeNotification();
                //Stop the service


                stopSelf();

            }

            @Override
            public void onSeekTo(long position) {
                super.onSeekTo(position);
            }
        });
    }

    public void sendSongsList() {
        ((MyApplication) getApplicationContext())
                .bus()
                .send(new EventBus.SendSongService(currentSongPosition, songList));
    }

    public void songProgrees() {
        ((MyApplication) getApplicationContext())
                .bus()
                .send(new EventBus.sendProgress(mediaPlayer.getDuration(), mediaPlayer.getCurrentPosition(), PlaybackStatus.PLAYING));
    }

    public void skipToNext() {

        mAppUtils = new AppUtils(getApplicationContext());
        songList = mAppUtils.loadAudio();

        currentSongPosition = mAppUtils.loadAudioIndex();

//
        if (PlayerService.isShuffle) {
            Random random = new Random();
            currentSongPosition = random.nextInt(songList.size());
        } else if (PlayerService.isRepeat) {

        } else {
            if (currentSongPosition == songList.size() - 1) {
                //if last in playlist
                currentSongPosition = 0;
            } else {
                //get next in playlist
                currentSongPosition = ++currentSongPosition;
                Log.d(TAG, "skipToNext: " + currentSongPosition);
            }
        }

        //reset mediaPlayer
        mAppUtils.storeAudioIndex(currentSongPosition);
        mediaPlayer.reset();
        initMediaPlayer();
    }


    public void seekToPosition(int position) {
        mediaPlayer.seekTo(position);
    }


    public void buildNotification(PlaybackStatus playbackStatus) {


        int notificationAction = R.drawable.ic_pause;//needs to be initialized
        PendingIntent play_pauseAction = null;

        mAppUtils = new AppUtils(getApplicationContext());
        songList = mAppUtils.loadAudio();

        currentSongPosition = mAppUtils.loadAudioIndex();


        Log.d(TAG, "buildNotification: " + playbackStatus);
        //Build a new notification according to the current state of the MediaPlayer
        if (playbackStatus == PlaybackStatus.PLAYING) {
            notificationAction = R.drawable.ic_pause;
            //create the pause action
            play_pauseAction = playbackAction(1);
        } else if (playbackStatus == PlaybackStatus.PAUSED) {
            notificationAction = R.drawable.ic_play_black;
            //create the play action
            play_pauseAction = playbackAction(0);
        }

        Uri sArtworkUri = Uri
                .parse("content://media/external/audio/albumart");

        Uri abumArturi = ContentUris.withAppendedId(sArtworkUri, songList.get(currentSongPosition).getAlbumID());

        Bitmap albumBitmap = null;
        try {
            albumBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), abumArturi);
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (albumBitmap == null) {
            albumBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.ic_music);
        }
        Log.d(TAG, "buildNotification: " + albumBitmap);

      notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = getApplicationContext().getResources().getString(R.string.text_notificationchannel);
        int importance = NotificationManager.IMPORTANCE_LOW;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, getApplicationContext().getResources().getString(R.string.text_notificationchannel), importance);
            notificationChannel.setDescription(getApplicationContext().getResources().getString(R.string.text_notificationchannel));
            notificationChannel.setSound(null, null);
            notificationManager.createNotificationChannel(notificationChannel);

        }

        NotificationCompat.Style style = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            style = new androidx.media.app.NotificationCompat.MediaStyle().setMediaSession(mediaSession.getSessionToken()).setShowActionsInCompactView(0, 1, 2);
        }


        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            notificationBuilder.setAutoCancel(true)
                    .setShowWhen(false)
                    .setSmallIcon(R.drawable.ic_music)
                    .setStyle(style)
                    .setLargeIcon(albumBitmap)
                    .setContentTitle(songList.get(currentSongPosition).getArtist())
                    .setColor(getResources().getColor(R.color.colorPrimaryDark))
                    .setContentText(songList.get(currentSongPosition).getAlbum())
                    .setContentInfo(songList.get(currentSongPosition).getTitle())
                    .addAction(R.drawable.ic_prev_black, getApplicationContext().getResources().getString(R.string.text_notification_prev), playbackAction(3))
                    .addAction(notificationAction, getApplicationContext().getResources().getString(R.string.text_notification_pause), play_pauseAction)
                    .addAction(R.drawable.ic_next_black, getApplicationContext().getResources().getString(R.string.text_notification_next), playbackAction(2))
                    .addAction(R.drawable.ic_close,getApplicationContext().getResources().getString(R.string.text_notification_close),playbackAction(4));


        }
        notificationManager.notify(1, notificationBuilder.build());
    }

    private Handler mSeekbarUpdateHandler = new Handler();
    private Runnable mUpdateSeekbar = new Runnable() {
        @Override
        public void run() {
            songProgrees();
            mSeekbarUpdateHandler.postDelayed(this, 50);
        }
    };


    private PendingIntent playbackAction(int actionNumber) {
        Intent playbackAction = new Intent(this, PlayerService.class);
        switch (actionNumber) {
            case 0:
                // Play
                playbackAction.setAction(ACTION_PLAY);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 1:
                // Pause
                playbackAction.setAction(ACTION_PAUSE);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 2:
                // Next track
                playbackAction.setAction(ACTION_NEXT);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);
            case 3:
                // Previous track
                playbackAction.setAction(ACTION_PREVIOUS);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);

            case 4:
                //stop the track
                playbackAction.setAction(ACTION_STOP);
                return PendingIntent.getService(this, actionNumber, playbackAction, 0);

            default:


                break;
        }
        return null;
    }


    public void updateMetaData() {

        Log.d(TAG, "updateMetaData: " + songList.size());
        Bitmap albumArt = BitmapFactory.decodeResource(getResources(),
                R.drawable.ic_music); //replace with medias albumArt
        // Update the current metadata
        mediaSession.setMetadata(new MediaMetadataCompat.Builder()
                .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, songList.get(currentSongPosition).getArtist())
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, songList.get(currentSongPosition).getAlbum())
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, songList.get(currentSongPosition).getTitle())
                .build());
    }

    private void playMedia() {
        Log.d(TAG, "playMedia: " + PlayerService.isPlaying);
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.start();
        }
        if (!PlayerService.isPlaying) {
            pauseMedia();
        }
    }

    private void stopMedia() {
        if (mediaPlayer == null) return;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.stop();
        }
    }

    public void pauseMedia() {
        isPlaying = false;
        if (mediaPlayer.isPlaying()) {
            mediaPlayer.pause();
            resumePosition = mediaPlayer.getCurrentPosition();
        }
        buildNotification(PlaybackStatus.PAUSED);
    }

    public void resumeMedia() {
        isPlaying = true;
        if (!mediaPlayer.isPlaying()) {
            mediaPlayer.seekTo(resumePosition);
            mediaPlayer.start();
        }
        buildNotification(PlaybackStatus.PLAYING);
    }


    private void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnCompletionListener(this);
        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setOnPreparedListener(this);
        mediaPlayer.setOnBufferingUpdateListener(this);
        mediaPlayer.setOnSeekCompleteListener(this);
        mediaPlayer.setOnInfoListener(this);

        //Reset so that the MediaPlayer is not pointing to another data source
        mediaPlayer.reset();

        Log.d(TAG, "initMediaPlayer: " + songList.get(currentSongPosition).getDisplayName());
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            // Set the data source to the mediaFile location
            mediaPlayer.setDataSource(songList.get(currentSongPosition).getPath());
        } catch (IOException e) {
            e.printStackTrace();
            stopSelf();
        }
        mediaPlayer.prepareAsync();
        mSeekbarUpdateHandler.postDelayed(mUpdateSeekbar, 0);

    }

    @Override
    public void onAudioFocusChange(int focusChange) {

        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                //resume playback
                if (mediaPlayer == null) {
                    initMediaPlayer();
                } else if (!mediaPlayer.isPlaying()) {
                    mediaPlayer.start();
                    mediaPlayer.setVolume(1.0f, 1.0f);
                }
                break;
            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (mediaPlayer.isPlaying()) mediaPlayer.pause();
                break;


            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;

        }

    }

    @Override
    public void onBufferingUpdate(MediaPlayer mp, int percent) {

    }


    private boolean requestAudioFocus() {
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN);
        if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            //Focus gained
            return true;
        }
        //Could not gain focus
        return false;
    }

    private boolean removeAudioFocus() {
        return AudioManager.AUDIOFOCUS_REQUEST_GRANTED ==
                audioManager.abandonAudioFocus(this);
    }


    @Override
    public void onCompletion(MediaPlayer mp) {
        //Invoked when playback of a media source has completed.

        Log.d(TAG, "onCompletion:repeat " + isRepeat);
        Log.d(TAG, "onCompletion:shuffle" + isShuffle);
        isPlaying = true;


        skipToNext();
        sendSongsList();
        updateMetaData();
        buildNotification(PlaybackStatus.PLAYING);


    }


    private void removeNotification() {
//        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.cancel(NOTIFICATION_ID);
        notificationManager.cancelAll();
        System.out.println("stop");
        System.exit(0);
    }


    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        //Invoked when there has been an error during an asynchronous operation
        switch (what) {
            case MediaPlayer.MEDIA_ERROR_NOT_VALID_FOR_PROGRESSIVE_PLAYBACK:
                Log.d("MediaPlayer Error", "MEDIA ERROR NOT VALID FOR PROGRESSIVE PLAYBACK " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_SERVER_DIED:
                Log.d("MediaPlayer Error", "MEDIA ERROR SERVER DIED " + extra);
                break;
            case MediaPlayer.MEDIA_ERROR_UNKNOWN:
                Log.d("MediaPlayer Error", "MEDIA ERROR UNKNOWN " + extra);
                break;
        }
        return false;
    }

    @Override
    public boolean onInfo(MediaPlayer mp, int what, int extra) {
        return false;
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        playMedia();
    }

    @Override
    public void onSeekComplete(MediaPlayer mp) {

    }


    public class LocalBinder extends Binder {
        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    public void skipToPrevious() {



        mAppUtils = new AppUtils(getApplicationContext());
        songList = mAppUtils.loadAudio();

        currentSongPosition = mAppUtils.loadAudioIndex();


        if (currentSongPosition == 0) {
            //if first in playlist
            //set index to the last of audioList
            currentSongPosition = songList.size() - 1;

        } else {

            currentSongPosition = --currentSongPosition;
        }
        //     }
        mAppUtils.storeAudioIndex(currentSongPosition);
        System.out.println("skip to prev" + currentSongPosition);

        stopMedia();
        //reset mediaPlayer
        mediaPlayer.reset();
        initMediaPlayer();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            stopMedia();
            mediaPlayer.release();
        }
        unregisterReceiver(mSongReceiver);
        // removeAudioFocus();
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        super.onTaskRemoved(rootIntent);
        System.out.println("task removed");
        removeNotification();
    }
}

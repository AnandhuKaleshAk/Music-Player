package com.music.darkmusicplayer.ui.songs;

import android.content.ContentUris;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import com.music.darkmusicplayer.EventBus;
import com.music.darkmusicplayer.MyApplication;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.AppRepository;
import com.music.darkmusicplayer.ui.main.MainActivity;
import com.music.darkmusicplayer.utils.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;


public class SongsListPresenter implements ISongListPresenter, LoaderManager.LoaderCallbacks<Cursor> {

    private ISongsListView mSongsListView;
    private static final int URL_LOAD_LOCAL_MUSIC = 0;

    private static  boolean isLoadedSongs=false;

    private static final String TAG = "SongsListPresenter";
    private static final Uri MEDIA_URI = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
    private static final String WHERE = MediaStore.Audio.Media.IS_MUSIC + "=1 AND "
            + MediaStore.Audio.Media.SIZE + ">0";
    private static final String ORDER_BY = MediaStore.Audio.Media.DISPLAY_NAME + " ASC";
    private static String[] PROJECTIONS = {
            MediaStore.Audio.Media._ID,
            MediaStore.Audio.Media.DATA, // the real path
            MediaStore.Audio.Media.TITLE,
            MediaStore.Audio.Media.DISPLAY_NAME,
            MediaStore.Audio.Media.MIME_TYPE,
            MediaStore.Audio.Media.ARTIST,
            MediaStore.Audio.Media.ALBUM,
            MediaStore.Audio.Media.IS_RINGTONE,
            MediaStore.Audio.Media.IS_MUSIC,
            MediaStore.Audio.Media.IS_NOTIFICATION,
            MediaStore.Audio.Media.DURATION,
            MediaStore.Audio.Media.SIZE,
            MediaStore.Audio.Media.ALBUM_ID,
            MediaStore.Audio.Media.DATE_ADDED,
    };

    private AppRepository mAppRepository;

    public SongsListPresenter(ISongsListView mSongsListView) {
        this.mSongsListView = mSongsListView;
        mAppRepository = new AppRepository(mSongsListView.getContext());
    }



    
    @Override
    public void loadLocalMusic() {
        mSongsListView.getLoaderManager().initLoader(URL_LOAD_LOCAL_MUSIC, null, this);
    }

    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int id, @Nullable Bundle args) {


        if (id != URL_LOAD_LOCAL_MUSIC) return null;
            return new CursorLoader(
                    mSongsListView.getContext(),
                    MEDIA_URI,
                    PROJECTIONS,
                    WHERE,
                    null,
                    ORDER_BY
            );

    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {

         String[] genresProj = {
                MediaStore.Audio.Genres.NAME,
                MediaStore.Audio.Genres._ID
        };



        Log.d(TAG, "onLoadFinished: " + data.getCount());
        Observable<List<Song>> observableSong = Observable.just(data)
                .map(new Function<Cursor, List<Song>>() {
                    @Override
                    public List<Song> apply(Cursor cursor) throws Exception {
                        final List<Song> songList = new ArrayList<>();
                        if (cursor != null && cursor.getCount() > 0) {
                            cursor.moveToFirst();
                            do {
                                Song song = FileUtils.cursorToMusic(cursor);
                                int songId=(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));

                                Uri uri = MediaStore.Audio.Genres.getContentUriForAudioId("external", songId );
                                   Cursor   genresCursor = mSongsListView.getContext().getContentResolver().query(uri,
                                        genresProj , null, null, null);
                                int genreIndex = genresCursor.getColumnIndexOrThrow(MediaStore.Audio.Genres.NAME);

                                String genreName=mSongsListView.getContext().getResources().getString(android.R.string.unknownName);
                                while (genresCursor.moveToNext()) {
                                    Log.d(TAG, "Genre = " +genresCursor.getString(genreIndex));
                                    genreName=genresCursor.getString(genreIndex);
                                }

                                song.setSongId(songId);
                                song.setGenreName(genreName);
                                songList.add(song);

                                if(songList.size()%20==0){
                                    Log.d(TAG, "apply: "+songList.size());
                                    loadSongs(songList);
                                 //  mSongsListView.onLocalMusicLoaded(songList);
                                }

                            } while (cursor.moveToNext());
                        }
                        loadSongs(songList);
                        return songList;

                    }
                });

        observableSong.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Song>>() {

                    @Override
                    public void onSubscribe(Disposable d) {
                   //     mSongsListView.addDisposible(d);
                    }

                    @Override
                    public void onNext(List<Song> songs) {
                        Log.d(TAG, "onNext: " + songs.size());
                        mAppRepository.insertSong(songs);

         //               mSongsListView.onLocalMusicLoaded(songs);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }

    public  void loadSongs(List<Song> songs){
        Log.d(TAG, "loadSongs: "+songs.size());

                ((MyApplication) mSongsListView.getContext().getApplicationContext())
                    .bus()
                    .send(new EventBus.UpdateSongList(songs));



        // mSongsListView.onLocalMusicLoaded(songs);
    }


}



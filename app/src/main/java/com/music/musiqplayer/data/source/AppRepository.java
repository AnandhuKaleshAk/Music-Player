package com.music.musiqplayer.data.source;

import android.content.Context;
import android.util.Log;


import com.music.musiqplayer.data.model.Album;
import com.music.musiqplayer.data.model.Artist;
import com.music.musiqplayer.data.model.Folder;
import com.music.musiqplayer.data.model.Genres;
import com.music.musiqplayer.data.model.PlayList;
import com.music.musiqplayer.data.model.Song;
import com.music.musiqplayer.data.source.dao.FolderDao;
import com.music.musiqplayer.data.source.dao.PlayListDao;
import com.music.musiqplayer.data.source.dao.SongDao;
import com.music.musiqplayer.utils.DBUtils;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;


public class AppRepository {

    private SongDao mSongDao;
    private FolderDao mFolderDao;
    private PlayListDao mPlayListDao;

    private static final String TAG = "AppRepository";

    public AppRepository(Context context) {
        AppDataBase database = AppDataBase.getInstance(context);
        mSongDao = database.songDao();
        mFolderDao = database.folderDao();
        mPlayListDao=database.playListDao();
    }

    public void insertSong(List<Song> songList) {
        Observable<List<Song>> listObservable = Observable.fromCallable(() -> {
            for (Song song : songList) {
                Song isSong = mSongDao.getSong(song.getTitle());
                if (isSong == null) {
                    mSongDao.insert(song);
                }
            }

            return songList;
        });
        listObservable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Song>>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(List<Song> songs) {
                        Log.d(TAG, "onNext:repos " + songs.size());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }


    public Observable<List<Album>> getAlbums() {
        return Observable.fromCallable(()
                -> mSongDao.getAlbums());
    }

    public Observable<List<Artist>> getArtists() {
        return Observable.fromCallable(()
                -> mSongDao.getArtist());
    }

    public Observable<List<Genres>> getGenres(){
        return Observable.fromCallable(()
                -> mSongDao.getGenres());
    }


    public Observable<List<Song>> getAlbumSong(String albumID) {
        return Observable.fromCallable(()
                -> mSongDao.getAlbumSong(albumID));

    }

    public Observable<List<Song>> getArtistSong(String artistName) {
        return Observable.fromCallable(()
                -> mSongDao.getArtistSong(artistName));
    }

    public Observable<List<Song>> getSearchSong(String songName) {
        return Observable.fromCallable(()
                -> mSongDao.getSearchSong(songName));

    }

    public Observable<List<Song>> getGenreSongs(String genreName) {
        return Observable.fromCallable(()
                -> mSongDao.getGenreSongs(genreName));

    }

    public Observable<Long> insertPlayList(PlayList playList){
        return Observable.fromCallable(()
                -> mPlayListDao.insert(playList));
    }

    public Observable<Song> getSong(String title){
        return Observable.fromCallable(()
          ->mSongDao.getSong(title)
        );
    }

    public Observable<List<PlayList>> getPlaylist(){
        return Observable.fromCallable(()
                ->mPlayListDao.getPlayList());
    }

    public Observable<Integer> deletePlaylist(int playListid){
        return Observable.fromCallable(()
                ->mPlayListDao.deletePlaylist(playListid));
    }



    public Observable<Integer> updatePlaylist(PlayList playList){
        return Observable.fromCallable(()
                ->mPlayListDao.updatePlayList(playList));
    }



    public Observable<Integer> updatePlayTime(long playTime,String title){
        return Observable.fromCallable(()
                ->mSongDao.updatePlayTime(playTime,title)
        );
    }

    public Observable<List<Song>> getAllSongs() {
        return Observable.fromCallable(()
                -> mSongDao.getAllSong());

    }

    public Observable<List<Song>> getLastPlayed() {
        return Observable.fromCallable(()
                -> mSongDao.getLastPlayed());

    }


    public Observable<List<Song>> getFavouriteSongs(){
        return Observable.fromCallable(()
                -> mSongDao.getFavouriteSong());

    }

    public Observable<List<Folder>> getFolders() {
        return Observable.create(emitter -> {
            List<Folder> defaultFolders = DBUtils.generateDefaultFolders();
            mFolderDao.deleteFolder();
            for (Folder folder : defaultFolders) {
                mFolderDao.insert(folder);
            }
            List<Folder> folderList = mFolderDao.getFolder();
            emitter.onNext(folderList);
            emitter.onComplete();
        });
    }

    public Observable<List<Folder>> getSavedFolders() {
        return Observable.create(emitter -> {
            List<Folder> folderList = mFolderDao.getFolder();
            emitter.onNext(folderList);
            emitter.onComplete();
        });
    }

    public Observable<Integer> insertFavourites(Boolean value, String title){
        return Observable.fromCallable(()
                -> mSongDao.insertFavourites(value,title));

    }


}

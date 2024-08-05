package com.music.musiqplayer.utils;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.BaseColumns;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.music.musiqplayer.MyApplication;
import com.music.musiqplayer.data.model.Folder;
import com.music.musiqplayer.data.model.Song;

import java.io.File;
import java.io.FileFilter;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

public class FileUtils {

    private static final String UNKNOWN = "unknown";
    private static final String TAG = "FileUtils";


    public static Folder folderFromDir(File dir) {
        Folder folder = new Folder();

        List<Song> songList = musicFiles(dir);
        folder.setName(dir.getName());
        folder.setPath(dir.getAbsolutePath());
        folder.setSongs((ArrayList<Song>) songList);
        folder.setNoOfSongs(songList.size());
        return folder;
    }

    public static String getRealPathFromURI(Context context, Uri uri){
        String selection = null;
        String[] selectionArgs = null;
        // Uri is different in versions after KITKAT (Android 4.4), we need to
        if (Build.VERSION.SDK_INT >= 19 && DocumentsContract.isDocumentUri(context.getApplicationContext(), uri)) {
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                return Environment.getExternalStorageDirectory() + "/" + split[1];
            } else if (isDownloadsDocument(uri)) {
                final String id = DocumentsContract.getDocumentId(uri);
                uri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
            } else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("image".equals(type)) {
                    uri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }
                selection = "_id=?";
                selectionArgs = new String[]{
                        split[1]
                };
            }
        }
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            if (isGooglePhotosUri(uri)) {
                return uri.getLastPathSegment();
            }

            String[] projection = {
                    MediaStore.Images.Media.DATA
            };
            Cursor cursor = null;
            try {
                cursor = context.getContentResolver()
                        .query(uri, projection, selection, selectionArgs, null);
                int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }


    public static HashSet<File> foldersFromSdCard(File dir, HashSet<File> mFileList) {

        File[] sdCardfiles = dir.listFiles();
        if (sdCardfiles != null) {
            for (File file : sdCardfiles) {
                if (file != null) {
                    if (file.isDirectory()) {
                        if(!isAndroidDirectory(file)) {
                            if(!isCacheDirectory(file)){
                                foldersFromSdCard(file, mFileList);
                            }
                        }
                    } else {
                        if (isMusic(file)) {
                                Log.d(TAG, "foldersFromSdCard: " + file.getParent());
                                mFileList.add(file.getParentFile());
                        }
                    }

                }
            }
        }


        return mFileList;
    }


    public static void deleteTracks(long[] list,Context context){
        final String[] projection=new String[]{
                BaseColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM_ID
        };
      final StringBuilder selection=new StringBuilder();
        selection.append(BaseColumns._ID + " IN (");
        for (int i = 0; i < list.length; i++) {
            selection.append(list[i]);
            if (i < list.length - 1) {
                selection.append(",");
            }
        }

        selection.append(")");
        final Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection.toString(),
                null, null);

        //remove the track from device

        context.getContentResolver().delete(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                selection.toString(), null);

        // Step 3: Remove files from card
        c.moveToFirst();
        while (!c.isAfterLast()) {
            final String name = c.getString(1);
            final File f = new File(name);
            try { // File.delete can throw a security exception
                if (!f.delete()) {
                    // I'm not sure if we'd ever get here (deletion would
                    // have to fail, but no exception thrown)
                    Log.e("MusicUtils", "Failed to delete file " + name);
                }
                c.moveToNext();
            } catch (final SecurityException ex) {
                c.moveToNext();
            }
        }
        c.close();


    }

    public static List<Song> musicFiles(File dir) {
        List<Song> songs = new ArrayList<>();


        if (dir != null && dir.isDirectory()) {

            ArrayList<File> fileList = new ArrayList<>();
            final File[] files = dir.listFiles(new FileFilter() {
                @Override
                public boolean accept(File item) {
                    if (isMusic(item)) {

                        fileList.add(item);
                    }
                    return item.isFile() && isMusic(item);
                }
            });


            for (File file : fileList) {
                Cursor songCursor=null;
                try {
                    Uri uri=Uri.fromFile(file);
                    String path = new File (new URI(uri.toString())).getAbsolutePath();
                    Log.d(TAG, "musicFiles: "+path);
                    songCursor = (MyApplication.getAppContext()).getContentResolver().query(
                            MediaStore.Audio.Media.EXTERNAL_CONTENT_URI,
                            new String[] {
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
                                    MediaStore.Audio.Media.DATE_ADDED
                            },
                            MediaStore.Audio.Media.DATA + " = ?",
                            new String[] {
                                   path
                            },
                            "");
                } catch (URISyntaxException e) {
                    System.out.println("expection");
                    e.printStackTrace();
                }

                try{
                    songCursor.moveToFirst();
                    Song song = cursorToMusic(songCursor);
                    if (song != null) {
                        songs.add(song);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }


            }
            if (songs.size() > 1) {
                Collections.sort(songs, (left, right) -> left.getTitle().compareTo(right.getTitle()));

            }
        }
        return songs;
    }



    public static boolean isAndroidDirectory(File file) {
        return file.getName().matches("Android");
    }

    public static boolean isCacheDirectory(File file) {
        Log.d(TAG, "isCacheDirectory: "+file.getName());
        return file.getName().startsWith(".");

    }
    public static boolean isMusic(File file) {
        final String REGEX = "(.*/)*.+\\.(mp3||wav|)$";
        return file.getName().matches(REGEX);
    }



    public static void shareTrack(final Context context, long id) {

        try {
            Intent share = new Intent(Intent.ACTION_SEND);
            share.setType("audio/*");
            share.putExtra(Intent.EXTRA_STREAM, getSongUri(context, id));
            context.startActivity(Intent.createChooser(share, "Share"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Uri getSongUri(Context context, long id) {
        final String[] projection = new String[]{
                BaseColumns._ID, MediaStore.MediaColumns.DATA, MediaStore.Audio.AudioColumns.ALBUM_ID
        };
        final StringBuilder selection = new StringBuilder();
        selection.append(BaseColumns._ID + " IN (");
        selection.append(id);
        selection.append(")");
        final Cursor c = context.getContentResolver().query(
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, projection, selection.toString(),
                null, null);

        if (c == null) {
            return null;
        }
        c.moveToFirst();


        try {

            Uri uri = Uri.parse(c.getString(1));
            c.close();

            return uri;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    public  static  Song cursorToMusic(Cursor cursor) {
        String realPath = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA));
        Song song;
        song = new Song();

        song.setId(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media._ID)));
        song.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.TITLE)));
        String displayName = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DISPLAY_NAME));
        if (displayName.endsWith(".mp3")) {
            displayName = displayName.substring(0, displayName.length() - 4);
        }
        song.setDisplayName(displayName);
        song.setArtist(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ARTIST)));
        song.setAlbum(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.ALBUM)));
        song.setPath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA)));
        song.setDuration(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DURATION)));
        song.setSize(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.SIZE)));
        song.setAlbumID(Long.parseLong(cursor.getString(cursor.getColumnIndex(MediaStore.Audio.Media.ALBUM_ID))));
        song.setAddedTime(cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATE_ADDED)));
        song.setPlayedTime(0);

        return song;
    }


}

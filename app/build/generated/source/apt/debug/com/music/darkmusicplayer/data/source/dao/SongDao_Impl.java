package com.music.darkmusicplayer.data.source.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.music.darkmusicplayer.data.model.Album;
import com.music.darkmusicplayer.data.model.Artist;
import com.music.darkmusicplayer.data.model.Genres;
import com.music.darkmusicplayer.data.model.Song;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SongDao_Impl implements SongDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfSong;

  private final SharedSQLiteStatement __preparedStmtOfInsertFavourites;

  private final SharedSQLiteStatement __preparedStmtOfUpdatePlayTime;

  public SongDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfSong = new EntityInsertionAdapter<Song>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `song`(`id`,`songId`,`title`,`displayName`,`artist`,`album`,`path`,`duration`,`size`,`favorite`,`albumID`,`addedTime`,`playedTime`,`genreName`) VALUES (nullif(?, 0),?,?,?,?,?,?,?,?,?,?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Song value) {
        stmt.bindLong(1, value.getId());
        stmt.bindLong(2, value.getSongId());
        if (value.getTitle() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getTitle());
        }
        if (value.getDisplayName() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, value.getDisplayName());
        }
        if (value.getArtist() == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, value.getArtist());
        }
        if (value.getAlbum() == null) {
          stmt.bindNull(6);
        } else {
          stmt.bindString(6, value.getAlbum());
        }
        if (value.getPath() == null) {
          stmt.bindNull(7);
        } else {
          stmt.bindString(7, value.getPath());
        }
        stmt.bindLong(8, value.getDuration());
        stmt.bindLong(9, value.getSize());
        final int _tmp;
        _tmp = value.isFavorite() ? 1 : 0;
        stmt.bindLong(10, _tmp);
        stmt.bindLong(11, value.getAlbumID());
        stmt.bindLong(12, value.getAddedTime());
        stmt.bindLong(13, value.getPlayedTime());
        if (value.getGenreName() == null) {
          stmt.bindNull(14);
        } else {
          stmt.bindString(14, value.getGenreName());
        }
      }
    };
    this.__preparedStmtOfInsertFavourites = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE song set favorite=? WHERE title=?";
        return _query;
      }
    };
    this.__preparedStmtOfUpdatePlayTime = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "UPDATE song set playedTime=? WHERE title=?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Song song) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfSong.insert(song);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int insertFavourites(final boolean val, final String title) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfInsertFavourites.acquire();
    int _argIndex = 1;
    final int _tmp;
    _tmp = val ? 1 : 0;
    _stmt.bindLong(_argIndex, _tmp);
    _argIndex = 2;
    if (title == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, title);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfInsertFavourites.release(_stmt);
    }
  }

  @Override
  public int updatePlayTime(final long playTime, final String title) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfUpdatePlayTime.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, playTime);
    _argIndex = 2;
    if (title == null) {
      _stmt.bindNull(_argIndex);
    } else {
      _stmt.bindString(_argIndex, title);
    }
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfUpdatePlayTime.release(_stmt);
    }
  }

  @Override
  public List<Album> getAlbums() {
    final String _sql = "SELECT DISTINCT albumID,album from song";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final List<Album> _result = new ArrayList<Album>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Album _item;
        _item = new Album();
        _item.albumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.album = _cursor.getString(_cursorIndexOfAlbum);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Artist> getArtist() {
    final String _sql = "SELECT DISTINCT artist,albumID,album from song";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final List<Artist> _result = new ArrayList<Artist>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Artist _item;
        _item = new Artist();
        _item.artist = _cursor.getString(_cursorIndexOfArtist);
        _item.albumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.album = _cursor.getString(_cursorIndexOfAlbum);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Genres> getGenres() {
    final String _sql = "SELECT DISTINCT albumID,album,genreName from song";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Genres> _result = new ArrayList<Genres>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Genres _item;
        _item = new Genres();
        _item.albumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.album = _cursor.getString(_cursorIndexOfAlbum);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Song getSong(final String title) {
    final String _sql = "SELECT *  from song WHERE title=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (title == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, title);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final Song _result;
      if(_cursor.moveToFirst()) {
        _result = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _result.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _result.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _result.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _result.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _result.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _result.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _result.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _result.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _result.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _result.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _result.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _result.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _result.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _result.setGenreName(_tmpGenreName);
      } else {
        _result = null;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getAlbumSong(final String albumID) {
    final String _sql = "SELECT *  from song WHERE albumID=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (albumID == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, albumID);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getArtistSong(final String artistName) {
    final String _sql = "SELECT *  from song WHERE artist=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (artistName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, artistName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getSearchSong(final String songName) {
    final String _sql = "SELECT *  from song WHERE title LIKE '%' || ? || '%'";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (songName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, songName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getGenreSongs(final String genreName) {
    final String _sql = "SELECT *  from song WHERE genreName=?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    if (genreName == null) {
      _statement.bindNull(_argIndex);
    } else {
      _statement.bindString(_argIndex, genreName);
    }
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getAllSong() {
    final String _sql = "SELECT *  from song";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getLastPlayed() {
    final String _sql = "SELECT *  from song WHERE playedTime!=0";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<Song> getFavouriteSong() {
    final String _sql = "SELECT *  from song WHERE favorite=1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfSongId = CursorUtil.getColumnIndexOrThrow(_cursor, "songId");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfDisplayName = CursorUtil.getColumnIndexOrThrow(_cursor, "displayName");
      final int _cursorIndexOfArtist = CursorUtil.getColumnIndexOrThrow(_cursor, "artist");
      final int _cursorIndexOfAlbum = CursorUtil.getColumnIndexOrThrow(_cursor, "album");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfDuration = CursorUtil.getColumnIndexOrThrow(_cursor, "duration");
      final int _cursorIndexOfSize = CursorUtil.getColumnIndexOrThrow(_cursor, "size");
      final int _cursorIndexOfFavorite = CursorUtil.getColumnIndexOrThrow(_cursor, "favorite");
      final int _cursorIndexOfAlbumID = CursorUtil.getColumnIndexOrThrow(_cursor, "albumID");
      final int _cursorIndexOfAddedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "addedTime");
      final int _cursorIndexOfPlayedTime = CursorUtil.getColumnIndexOrThrow(_cursor, "playedTime");
      final int _cursorIndexOfGenreName = CursorUtil.getColumnIndexOrThrow(_cursor, "genreName");
      final List<Song> _result = new ArrayList<Song>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Song _item;
        _item = new Song();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final int _tmpSongId;
        _tmpSongId = _cursor.getInt(_cursorIndexOfSongId);
        _item.setSongId(_tmpSongId);
        final String _tmpTitle;
        _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        _item.setTitle(_tmpTitle);
        final String _tmpDisplayName;
        _tmpDisplayName = _cursor.getString(_cursorIndexOfDisplayName);
        _item.setDisplayName(_tmpDisplayName);
        final String _tmpArtist;
        _tmpArtist = _cursor.getString(_cursorIndexOfArtist);
        _item.setArtist(_tmpArtist);
        final String _tmpAlbum;
        _tmpAlbum = _cursor.getString(_cursorIndexOfAlbum);
        _item.setAlbum(_tmpAlbum);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpDuration;
        _tmpDuration = _cursor.getInt(_cursorIndexOfDuration);
        _item.setDuration(_tmpDuration);
        final int _tmpSize;
        _tmpSize = _cursor.getInt(_cursorIndexOfSize);
        _item.setSize(_tmpSize);
        final boolean _tmpFavorite;
        final int _tmp;
        _tmp = _cursor.getInt(_cursorIndexOfFavorite);
        _tmpFavorite = _tmp != 0;
        _item.setFavorite(_tmpFavorite);
        final long _tmpAlbumID;
        _tmpAlbumID = _cursor.getLong(_cursorIndexOfAlbumID);
        _item.setAlbumID(_tmpAlbumID);
        final int _tmpAddedTime;
        _tmpAddedTime = _cursor.getInt(_cursorIndexOfAddedTime);
        _item.setAddedTime(_tmpAddedTime);
        final int _tmpPlayedTime;
        _tmpPlayedTime = _cursor.getInt(_cursorIndexOfPlayedTime);
        _item.setPlayedTime(_tmpPlayedTime);
        final String _tmpGenreName;
        _tmpGenreName = _cursor.getString(_cursorIndexOfGenreName);
        _item.setGenreName(_tmpGenreName);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

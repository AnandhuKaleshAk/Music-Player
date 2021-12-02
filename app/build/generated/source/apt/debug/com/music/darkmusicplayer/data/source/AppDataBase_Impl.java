package com.music.darkmusicplayer.data.source;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import com.music.darkmusicplayer.data.source.dao.FolderDao;
import com.music.darkmusicplayer.data.source.dao.FolderDao_Impl;
import com.music.darkmusicplayer.data.source.dao.PlayListDao;
import com.music.darkmusicplayer.data.source.dao.PlayListDao_Impl;
import com.music.darkmusicplayer.data.source.dao.SongDao;
import com.music.darkmusicplayer.data.source.dao.SongDao_Impl;
import java.lang.IllegalStateException;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AppDataBase_Impl extends AppDataBase {
  private volatile SongDao _songDao;

  private volatile FolderDao _folderDao;

  private volatile PlayListDao _playListDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `song` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `songId` INTEGER NOT NULL, `title` TEXT, `displayName` TEXT, `artist` TEXT, `album` TEXT, `path` TEXT, `duration` INTEGER NOT NULL, `size` INTEGER NOT NULL, `favorite` INTEGER NOT NULL, `albumID` INTEGER NOT NULL, `addedTime` INTEGER NOT NULL, `playedTime` INTEGER NOT NULL, `genreName` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `folder` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `path` TEXT, `noOfSongs` INTEGER NOT NULL, `songs` TEXT)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `playlist` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `playListName` TEXT, `noOfSongs` INTEGER NOT NULL, `songs` TEXT, `imageId` INTEGER NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '093a1d309c8aeb125b694ad3cd0f87d0')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `song`");
        _db.execSQL("DROP TABLE IF EXISTS `folder`");
        _db.execSQL("DROP TABLE IF EXISTS `playlist`");
      }

      @Override
      protected void onCreate(SupportSQLiteDatabase _db) {
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onCreate(_db);
          }
        }
      }

      @Override
      public void onOpen(SupportSQLiteDatabase _db) {
        mDatabase = _db;
        internalInitInvalidationTracker(_db);
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onOpen(_db);
          }
        }
      }

      @Override
      public void onPreMigrate(SupportSQLiteDatabase _db) {
        DBUtil.dropFtsSyncTriggers(_db);
      }

      @Override
      public void onPostMigrate(SupportSQLiteDatabase _db) {
      }

      @Override
      protected void validateMigration(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSong = new HashMap<String, TableInfo.Column>(14);
        _columnsSong.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsSong.put("songId", new TableInfo.Column("songId", "INTEGER", true, 0));
        _columnsSong.put("title", new TableInfo.Column("title", "TEXT", false, 0));
        _columnsSong.put("displayName", new TableInfo.Column("displayName", "TEXT", false, 0));
        _columnsSong.put("artist", new TableInfo.Column("artist", "TEXT", false, 0));
        _columnsSong.put("album", new TableInfo.Column("album", "TEXT", false, 0));
        _columnsSong.put("path", new TableInfo.Column("path", "TEXT", false, 0));
        _columnsSong.put("duration", new TableInfo.Column("duration", "INTEGER", true, 0));
        _columnsSong.put("size", new TableInfo.Column("size", "INTEGER", true, 0));
        _columnsSong.put("favorite", new TableInfo.Column("favorite", "INTEGER", true, 0));
        _columnsSong.put("albumID", new TableInfo.Column("albumID", "INTEGER", true, 0));
        _columnsSong.put("addedTime", new TableInfo.Column("addedTime", "INTEGER", true, 0));
        _columnsSong.put("playedTime", new TableInfo.Column("playedTime", "INTEGER", true, 0));
        _columnsSong.put("genreName", new TableInfo.Column("genreName", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSong = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSong = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSong = new TableInfo("song", _columnsSong, _foreignKeysSong, _indicesSong);
        final TableInfo _existingSong = TableInfo.read(_db, "song");
        if (! _infoSong.equals(_existingSong)) {
          throw new IllegalStateException("Migration didn't properly handle song(com.music.darkmusicplayer.data.model.Song).\n"
                  + " Expected:\n" + _infoSong + "\n"
                  + " Found:\n" + _existingSong);
        }
        final HashMap<String, TableInfo.Column> _columnsFolder = new HashMap<String, TableInfo.Column>(5);
        _columnsFolder.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsFolder.put("name", new TableInfo.Column("name", "TEXT", false, 0));
        _columnsFolder.put("path", new TableInfo.Column("path", "TEXT", false, 0));
        _columnsFolder.put("noOfSongs", new TableInfo.Column("noOfSongs", "INTEGER", true, 0));
        _columnsFolder.put("songs", new TableInfo.Column("songs", "TEXT", false, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysFolder = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesFolder = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoFolder = new TableInfo("folder", _columnsFolder, _foreignKeysFolder, _indicesFolder);
        final TableInfo _existingFolder = TableInfo.read(_db, "folder");
        if (! _infoFolder.equals(_existingFolder)) {
          throw new IllegalStateException("Migration didn't properly handle folder(com.music.darkmusicplayer.data.model.Folder).\n"
                  + " Expected:\n" + _infoFolder + "\n"
                  + " Found:\n" + _existingFolder);
        }
        final HashMap<String, TableInfo.Column> _columnsPlaylist = new HashMap<String, TableInfo.Column>(5);
        _columnsPlaylist.put("id", new TableInfo.Column("id", "INTEGER", true, 1));
        _columnsPlaylist.put("playListName", new TableInfo.Column("playListName", "TEXT", false, 0));
        _columnsPlaylist.put("noOfSongs", new TableInfo.Column("noOfSongs", "INTEGER", true, 0));
        _columnsPlaylist.put("songs", new TableInfo.Column("songs", "TEXT", false, 0));
        _columnsPlaylist.put("imageId", new TableInfo.Column("imageId", "INTEGER", true, 0));
        final HashSet<TableInfo.ForeignKey> _foreignKeysPlaylist = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesPlaylist = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoPlaylist = new TableInfo("playlist", _columnsPlaylist, _foreignKeysPlaylist, _indicesPlaylist);
        final TableInfo _existingPlaylist = TableInfo.read(_db, "playlist");
        if (! _infoPlaylist.equals(_existingPlaylist)) {
          throw new IllegalStateException("Migration didn't properly handle playlist(com.music.darkmusicplayer.data.model.PlayList).\n"
                  + " Expected:\n" + _infoPlaylist + "\n"
                  + " Found:\n" + _existingPlaylist);
        }
      }
    }, "093a1d309c8aeb125b694ad3cd0f87d0", "acd9531bf5511310a2583b9e677c27da");
    final SupportSQLiteOpenHelper.Configuration _sqliteConfig = SupportSQLiteOpenHelper.Configuration.builder(configuration.context)
        .name(configuration.name)
        .callback(_openCallback)
        .build();
    final SupportSQLiteOpenHelper _helper = configuration.sqliteOpenHelperFactory.create(_sqliteConfig);
    return _helper;
  }

  @Override
  protected InvalidationTracker createInvalidationTracker() {
    final HashMap<String, String> _shadowTablesMap = new HashMap<String, String>(0);
    HashMap<String, Set<String>> _viewTables = new HashMap<String, Set<String>>(0);
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "song","folder","playlist");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `song`");
      _db.execSQL("DELETE FROM `folder`");
      _db.execSQL("DELETE FROM `playlist`");
      super.setTransactionSuccessful();
    } finally {
      super.endTransaction();
      _db.query("PRAGMA wal_checkpoint(FULL)").close();
      if (!_db.inTransaction()) {
        _db.execSQL("VACUUM");
      }
    }
  }

  @Override
  public SongDao songDao() {
    if (_songDao != null) {
      return _songDao;
    } else {
      synchronized(this) {
        if(_songDao == null) {
          _songDao = new SongDao_Impl(this);
        }
        return _songDao;
      }
    }
  }

  @Override
  public FolderDao folderDao() {
    if (_folderDao != null) {
      return _folderDao;
    } else {
      synchronized(this) {
        if(_folderDao == null) {
          _folderDao = new FolderDao_Impl(this);
        }
        return _folderDao;
      }
    }
  }

  @Override
  public PlayListDao playListDao() {
    if (_playListDao != null) {
      return _playListDao;
    } else {
      synchronized(this) {
        if(_playListDao == null) {
          _playListDao = new PlayListDao_Impl(this);
        }
        return _playListDao;
      }
    }
  }
}

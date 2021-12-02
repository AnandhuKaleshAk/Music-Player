package com.music.darkmusicplayer.data.source.dao;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.music.darkmusicplayer.data.model.Folder;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.DataConverter;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class FolderDao_Impl implements FolderDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfFolder;

  private final DataConverter __dataConverter = new DataConverter();

  private final SharedSQLiteStatement __preparedStmtOfDeleteFolder;

  public FolderDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfFolder = new EntityInsertionAdapter<Folder>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `folder`(`id`,`name`,`path`,`noOfSongs`,`songs`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Folder value) {
        stmt.bindLong(1, value.getId());
        if (value.getName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getName());
        }
        if (value.getPath() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getPath());
        }
        stmt.bindLong(4, value.getNoOfSongs());
        final String _tmp;
        _tmp = __dataConverter.fromFolderList(value.getSongs());
        if (_tmp == null) {
          stmt.bindNull(5);
        } else {
          stmt.bindString(5, _tmp);
        }
      }
    };
    this.__preparedStmtOfDeleteFolder = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  from folder";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Folder folder) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfFolder.insert(folder);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deleteFolder() {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeleteFolder.acquire();
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeleteFolder.release(_stmt);
    }
  }

  @Override
  public List<Folder> getFolder() {
    final String _sql = "SELECT *  from folder";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfName = CursorUtil.getColumnIndexOrThrow(_cursor, "name");
      final int _cursorIndexOfPath = CursorUtil.getColumnIndexOrThrow(_cursor, "path");
      final int _cursorIndexOfNoOfSongs = CursorUtil.getColumnIndexOrThrow(_cursor, "noOfSongs");
      final int _cursorIndexOfSongs = CursorUtil.getColumnIndexOrThrow(_cursor, "songs");
      final List<Folder> _result = new ArrayList<Folder>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Folder _item;
        _item = new Folder();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpName;
        _tmpName = _cursor.getString(_cursorIndexOfName);
        _item.setName(_tmpName);
        final String _tmpPath;
        _tmpPath = _cursor.getString(_cursorIndexOfPath);
        _item.setPath(_tmpPath);
        final int _tmpNoOfSongs;
        _tmpNoOfSongs = _cursor.getInt(_cursorIndexOfNoOfSongs);
        _item.setNoOfSongs(_tmpNoOfSongs);
        final List<Song> _tmpSongs;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfSongs);
        _tmpSongs = __dataConverter.toFolderList(_tmp);
        _item.setSongs(_tmpSongs);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

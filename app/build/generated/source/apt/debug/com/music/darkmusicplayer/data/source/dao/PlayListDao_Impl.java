package com.music.darkmusicplayer.data.source.dao;

import android.database.Cursor;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import com.music.darkmusicplayer.data.model.PlayList;
import com.music.darkmusicplayer.data.model.Song;
import com.music.darkmusicplayer.data.source.DataConverter;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class PlayListDao_Impl implements PlayListDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPlayList;

  private final DataConverter __dataConverter = new DataConverter();

  private final EntityDeletionOrUpdateAdapter __updateAdapterOfPlayList;

  private final SharedSQLiteStatement __preparedStmtOfDeletePlaylist;

  public PlayListDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPlayList = new EntityInsertionAdapter<PlayList>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `playlist`(`id`,`playListName`,`noOfSongs`,`songs`,`imageId`) VALUES (nullif(?, 0),?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayList value) {
        stmt.bindLong(1, value.getId());
        if (value.getPlayListName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPlayListName());
        }
        stmt.bindLong(3, value.getNoOfSongs());
        final String _tmp;
        _tmp = __dataConverter.fromFolderList(value.getSongs());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        stmt.bindLong(5, value.getImageId());
      }
    };
    this.__updateAdapterOfPlayList = new EntityDeletionOrUpdateAdapter<PlayList>(__db) {
      @Override
      public String createQuery() {
        return "UPDATE OR ABORT `playlist` SET `id` = ?,`playListName` = ?,`noOfSongs` = ?,`songs` = ?,`imageId` = ? WHERE `id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PlayList value) {
        stmt.bindLong(1, value.getId());
        if (value.getPlayListName() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPlayListName());
        }
        stmt.bindLong(3, value.getNoOfSongs());
        final String _tmp;
        _tmp = __dataConverter.fromFolderList(value.getSongs());
        if (_tmp == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindString(4, _tmp);
        }
        stmt.bindLong(5, value.getImageId());
        stmt.bindLong(6, value.getId());
      }
    };
    this.__preparedStmtOfDeletePlaylist = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE  from playlist  WHERE id=?";
        return _query;
      }
    };
  }

  @Override
  public long insert(final PlayList playList) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      long _result = __insertionAdapterOfPlayList.insertAndReturnId(playList);
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int updatePlayList(final PlayList playList) {
    __db.assertNotSuspendingTransaction();
    int _total = 0;
    __db.beginTransaction();
    try {
      _total +=__updateAdapterOfPlayList.handle(playList);
      __db.setTransactionSuccessful();
      return _total;
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public int deletePlaylist(final int playlistid) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDeletePlaylist.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, playlistid);
    __db.beginTransaction();
    try {
      final int _result = _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
      return _result;
    } finally {
      __db.endTransaction();
      __preparedStmtOfDeletePlaylist.release(_stmt);
    }
  }

  @Override
  public List<PlayList> getPlayList() {
    final String _sql = "SELECT *  from playlist";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfPlayListName = CursorUtil.getColumnIndexOrThrow(_cursor, "playListName");
      final int _cursorIndexOfNoOfSongs = CursorUtil.getColumnIndexOrThrow(_cursor, "noOfSongs");
      final int _cursorIndexOfSongs = CursorUtil.getColumnIndexOrThrow(_cursor, "songs");
      final int _cursorIndexOfImageId = CursorUtil.getColumnIndexOrThrow(_cursor, "imageId");
      final List<PlayList> _result = new ArrayList<PlayList>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PlayList _item;
        _item = new PlayList();
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        _item.setId(_tmpId);
        final String _tmpPlayListName;
        _tmpPlayListName = _cursor.getString(_cursorIndexOfPlayListName);
        _item.setPlayListName(_tmpPlayListName);
        final int _tmpNoOfSongs;
        _tmpNoOfSongs = _cursor.getInt(_cursorIndexOfNoOfSongs);
        _item.setNoOfSongs(_tmpNoOfSongs);
        final List<Song> _tmpSongs;
        final String _tmp;
        _tmp = _cursor.getString(_cursorIndexOfSongs);
        _tmpSongs = __dataConverter.toFolderList(_tmp);
        _item.setSongs(_tmpSongs);
        final int _tmpImageId;
        _tmpImageId = _cursor.getInt(_cursorIndexOfImageId);
        _item.setImageId(_tmpImageId);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }
}

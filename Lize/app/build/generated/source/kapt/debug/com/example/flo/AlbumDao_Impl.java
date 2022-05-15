package com.example.flo;

import android.database.Cursor;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.SharedSQLiteStatement;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.lang.Class;
import java.lang.Integer;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@SuppressWarnings({"unchecked", "deprecation"})
public final class AlbumDao_Impl implements AlbumDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter<Album> __insertionAdapterOfAlbum;

  private final EntityInsertionAdapter<Like> __insertionAdapterOfLike;

  private final SharedSQLiteStatement __preparedStmtOfDisLikedAlbum;

  public AlbumDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfAlbum = new EntityInsertionAdapter<Album>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `AlbumTable` (`id`,`title`,`singer`,`coverImg`) VALUES (?,?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Album value) {
        stmt.bindLong(1, value.getId());
        if (value.getTitle() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getTitle());
        }
        if (value.getSinger() == null) {
          stmt.bindNull(3);
        } else {
          stmt.bindString(3, value.getSinger());
        }
        if (value.getCoverImg() == null) {
          stmt.bindNull(4);
        } else {
          stmt.bindLong(4, value.getCoverImg());
        }
      }
    };
    this.__insertionAdapterOfLike = new EntityInsertionAdapter<Like>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR ABORT INTO `LikeTable` (`userId`,`albumId`,`id`) VALUES (?,?,nullif(?, 0))";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, Like value) {
        stmt.bindLong(1, value.getUserId());
        stmt.bindLong(2, value.getAlbumId());
        stmt.bindLong(3, value.getId());
      }
    };
    this.__preparedStmtOfDisLikedAlbum = new SharedSQLiteStatement(__db) {
      @Override
      public String createQuery() {
        final String _query = "DELETE FROM LikeTable WHERE userId = ? AND albumId = ?";
        return _query;
      }
    };
  }

  @Override
  public void insert(final Album album) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfAlbum.insert(album);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void likeAlbum(final Like like) {
    __db.assertNotSuspendingTransaction();
    __db.beginTransaction();
    try {
      __insertionAdapterOfLike.insert(like);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void disLikedAlbum(final int userId, final int albumId) {
    __db.assertNotSuspendingTransaction();
    final SupportSQLiteStatement _stmt = __preparedStmtOfDisLikedAlbum.acquire();
    int _argIndex = 1;
    _stmt.bindLong(_argIndex, userId);
    _argIndex = 2;
    _stmt.bindLong(_argIndex, albumId);
    __db.beginTransaction();
    try {
      _stmt.executeUpdateDelete();
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
      __preparedStmtOfDisLikedAlbum.release(_stmt);
    }
  }

  @Override
  public List<Album> getAlbums() {
    final String _sql = "SELECT * FROM AlbumTable";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfSinger = CursorUtil.getColumnIndexOrThrow(_cursor, "singer");
      final int _cursorIndexOfCoverImg = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImg");
      final List<Album> _result = new ArrayList<Album>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Album _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpSinger;
        if (_cursor.isNull(_cursorIndexOfSinger)) {
          _tmpSinger = null;
        } else {
          _tmpSinger = _cursor.getString(_cursorIndexOfSinger);
        }
        final Integer _tmpCoverImg;
        if (_cursor.isNull(_cursorIndexOfCoverImg)) {
          _tmpCoverImg = null;
        } else {
          _tmpCoverImg = _cursor.getInt(_cursorIndexOfCoverImg);
        }
        _item = new Album(_tmpId,_tmpTitle,_tmpSinger,_tmpCoverImg);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public Integer isLikedAlbum(final int userId, final int albumId) {
    final String _sql = "SELECT id FROM LikeTable WHERE userId = ? AND albumId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 2);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    _argIndex = 2;
    _statement.bindLong(_argIndex, albumId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final Integer _result;
      if(_cursor.moveToFirst()) {
        if (_cursor.isNull(0)) {
          _result = null;
        } else {
          _result = _cursor.getInt(0);
        }
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
  public List<Album> getLikedAlbum(final int userId) {
    final String _sql = "SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT ON LT.albumId = AT.id WHERE LT.userId = ?";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 1);
    int _argIndex = 1;
    _statement.bindLong(_argIndex, userId);
    __db.assertNotSuspendingTransaction();
    final Cursor _cursor = DBUtil.query(__db, _statement, false, null);
    try {
      final int _cursorIndexOfId = CursorUtil.getColumnIndexOrThrow(_cursor, "id");
      final int _cursorIndexOfTitle = CursorUtil.getColumnIndexOrThrow(_cursor, "title");
      final int _cursorIndexOfSinger = CursorUtil.getColumnIndexOrThrow(_cursor, "singer");
      final int _cursorIndexOfCoverImg = CursorUtil.getColumnIndexOrThrow(_cursor, "coverImg");
      final List<Album> _result = new ArrayList<Album>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final Album _item;
        final int _tmpId;
        _tmpId = _cursor.getInt(_cursorIndexOfId);
        final String _tmpTitle;
        if (_cursor.isNull(_cursorIndexOfTitle)) {
          _tmpTitle = null;
        } else {
          _tmpTitle = _cursor.getString(_cursorIndexOfTitle);
        }
        final String _tmpSinger;
        if (_cursor.isNull(_cursorIndexOfSinger)) {
          _tmpSinger = null;
        } else {
          _tmpSinger = _cursor.getString(_cursorIndexOfSinger);
        }
        final Integer _tmpCoverImg;
        if (_cursor.isNull(_cursorIndexOfCoverImg)) {
          _tmpCoverImg = null;
        } else {
          _tmpCoverImg = _cursor.getInt(_cursorIndexOfCoverImg);
        }
        _item = new Album(_tmpId,_tmpTitle,_tmpSinger,_tmpCoverImg);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  public static List<Class<?>> getRequiredConverters() {
    return Collections.emptyList();
  }
}

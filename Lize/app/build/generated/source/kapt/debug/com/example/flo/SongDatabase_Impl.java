package com.example.flo;

import androidx.annotation.NonNull;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomOpenHelper;
import androidx.room.RoomOpenHelper.Delegate;
import androidx.room.RoomOpenHelper.ValidationResult;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.room.util.TableInfo.Column;
import androidx.room.util.TableInfo.ForeignKey;
import androidx.room.util.TableInfo.Index;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Callback;
import androidx.sqlite.db.SupportSQLiteOpenHelper.Configuration;
import java.lang.Class;
import java.lang.Override;
import java.lang.String;
import java.lang.SuppressWarnings;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@SuppressWarnings({"unchecked", "deprecation"})
public final class SongDatabase_Impl extends SongDatabase {
  private volatile SongDao _songDao;

  private volatile UserDao _userDao;

  private volatile AlbumDao _albumDao;

  @Override
  protected SupportSQLiteOpenHelper createOpenHelper(DatabaseConfiguration configuration) {
    final SupportSQLiteOpenHelper.Callback _openCallback = new RoomOpenHelper(configuration, new RoomOpenHelper.Delegate(1) {
      @Override
      public void createAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("CREATE TABLE IF NOT EXISTS `SongTable` (`title` TEXT NOT NULL, `singer` TEXT NOT NULL, `second` INTEGER NOT NULL, `playTime` INTEGER NOT NULL, `isPlaying` INTEGER NOT NULL, `music` TEXT NOT NULL, `coverImg` INTEGER, `isLike` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `UserTable` (`email` TEXT NOT NULL, `password` TEXT NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `LikeTable` (`userId` INTEGER NOT NULL, `albumId` INTEGER NOT NULL, `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL)");
        _db.execSQL("CREATE TABLE IF NOT EXISTS `AlbumTable` (`id` INTEGER NOT NULL, `title` TEXT, `singer` TEXT, `coverImg` INTEGER, PRIMARY KEY(`id`))");
        _db.execSQL("CREATE TABLE IF NOT EXISTS room_master_table (id INTEGER PRIMARY KEY,identity_hash TEXT)");
        _db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, 'f63ef87472cde3957bba4d503bee1d4b')");
      }

      @Override
      public void dropAllTables(SupportSQLiteDatabase _db) {
        _db.execSQL("DROP TABLE IF EXISTS `SongTable`");
        _db.execSQL("DROP TABLE IF EXISTS `UserTable`");
        _db.execSQL("DROP TABLE IF EXISTS `LikeTable`");
        _db.execSQL("DROP TABLE IF EXISTS `AlbumTable`");
        if (mCallbacks != null) {
          for (int _i = 0, _size = mCallbacks.size(); _i < _size; _i++) {
            mCallbacks.get(_i).onDestructiveMigration(_db);
          }
        }
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
      protected RoomOpenHelper.ValidationResult onValidateSchema(SupportSQLiteDatabase _db) {
        final HashMap<String, TableInfo.Column> _columnsSongTable = new HashMap<String, TableInfo.Column>(9);
        _columnsSongTable.put("title", new TableInfo.Column("title", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("singer", new TableInfo.Column("singer", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("second", new TableInfo.Column("second", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("playTime", new TableInfo.Column("playTime", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("isPlaying", new TableInfo.Column("isPlaying", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("music", new TableInfo.Column("music", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("coverImg", new TableInfo.Column("coverImg", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("isLike", new TableInfo.Column("isLike", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsSongTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysSongTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesSongTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoSongTable = new TableInfo("SongTable", _columnsSongTable, _foreignKeysSongTable, _indicesSongTable);
        final TableInfo _existingSongTable = TableInfo.read(_db, "SongTable");
        if (! _infoSongTable.equals(_existingSongTable)) {
          return new RoomOpenHelper.ValidationResult(false, "SongTable(com.example.flo.Song).\n"
                  + " Expected:\n" + _infoSongTable + "\n"
                  + " Found:\n" + _existingSongTable);
        }
        final HashMap<String, TableInfo.Column> _columnsUserTable = new HashMap<String, TableInfo.Column>(3);
        _columnsUserTable.put("email", new TableInfo.Column("email", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("password", new TableInfo.Column("password", "TEXT", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsUserTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysUserTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesUserTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoUserTable = new TableInfo("UserTable", _columnsUserTable, _foreignKeysUserTable, _indicesUserTable);
        final TableInfo _existingUserTable = TableInfo.read(_db, "UserTable");
        if (! _infoUserTable.equals(_existingUserTable)) {
          return new RoomOpenHelper.ValidationResult(false, "UserTable(com.example.flo.User).\n"
                  + " Expected:\n" + _infoUserTable + "\n"
                  + " Found:\n" + _existingUserTable);
        }
        final HashMap<String, TableInfo.Column> _columnsLikeTable = new HashMap<String, TableInfo.Column>(3);
        _columnsLikeTable.put("userId", new TableInfo.Column("userId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikeTable.put("albumId", new TableInfo.Column("albumId", "INTEGER", true, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsLikeTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysLikeTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesLikeTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoLikeTable = new TableInfo("LikeTable", _columnsLikeTable, _foreignKeysLikeTable, _indicesLikeTable);
        final TableInfo _existingLikeTable = TableInfo.read(_db, "LikeTable");
        if (! _infoLikeTable.equals(_existingLikeTable)) {
          return new RoomOpenHelper.ValidationResult(false, "LikeTable(com.example.flo.Like).\n"
                  + " Expected:\n" + _infoLikeTable + "\n"
                  + " Found:\n" + _existingLikeTable);
        }
        final HashMap<String, TableInfo.Column> _columnsAlbumTable = new HashMap<String, TableInfo.Column>(4);
        _columnsAlbumTable.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlbumTable.put("title", new TableInfo.Column("title", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlbumTable.put("singer", new TableInfo.Column("singer", "TEXT", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        _columnsAlbumTable.put("coverImg", new TableInfo.Column("coverImg", "INTEGER", false, 0, null, TableInfo.CREATED_FROM_ENTITY));
        final HashSet<TableInfo.ForeignKey> _foreignKeysAlbumTable = new HashSet<TableInfo.ForeignKey>(0);
        final HashSet<TableInfo.Index> _indicesAlbumTable = new HashSet<TableInfo.Index>(0);
        final TableInfo _infoAlbumTable = new TableInfo("AlbumTable", _columnsAlbumTable, _foreignKeysAlbumTable, _indicesAlbumTable);
        final TableInfo _existingAlbumTable = TableInfo.read(_db, "AlbumTable");
        if (! _infoAlbumTable.equals(_existingAlbumTable)) {
          return new RoomOpenHelper.ValidationResult(false, "AlbumTable(com.example.flo.Album).\n"
                  + " Expected:\n" + _infoAlbumTable + "\n"
                  + " Found:\n" + _existingAlbumTable);
        }
        return new RoomOpenHelper.ValidationResult(true, null);
      }
    }, "f63ef87472cde3957bba4d503bee1d4b", "bfb6c1deb979504c8c2aca7b7c431cf7");
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
    return new InvalidationTracker(this, _shadowTablesMap, _viewTables, "SongTable","UserTable","LikeTable","AlbumTable");
  }

  @Override
  public void clearAllTables() {
    super.assertNotMainThread();
    final SupportSQLiteDatabase _db = super.getOpenHelper().getWritableDatabase();
    try {
      super.beginTransaction();
      _db.execSQL("DELETE FROM `SongTable`");
      _db.execSQL("DELETE FROM `UserTable`");
      _db.execSQL("DELETE FROM `LikeTable`");
      _db.execSQL("DELETE FROM `AlbumTable`");
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
  protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
    final HashMap<Class<?>, List<Class<?>>> _typeConvertersMap = new HashMap<Class<?>, List<Class<?>>>();
    _typeConvertersMap.put(SongDao.class, SongDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(UserDao.class, UserDao_Impl.getRequiredConverters());
    _typeConvertersMap.put(AlbumDao.class, AlbumDao_Impl.getRequiredConverters());
    return _typeConvertersMap;
  }

  @Override
  public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
    final HashSet<Class<? extends AutoMigrationSpec>> _autoMigrationSpecsSet = new HashSet<Class<? extends AutoMigrationSpec>>();
    return _autoMigrationSpecsSet;
  }

  @Override
  public List<Migration> getAutoMigrations(
      @NonNull Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecsMap) {
    return Arrays.asList();
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
  public UserDao userDao() {
    if (_userDao != null) {
      return _userDao;
    } else {
      synchronized(this) {
        if(_userDao == null) {
          _userDao = new UserDao_Impl(this);
        }
        return _userDao;
      }
    }
  }

  @Override
  public AlbumDao albumDao() {
    if (_albumDao != null) {
      return _albumDao;
    } else {
      synchronized(this) {
        if(_albumDao == null) {
          _albumDao = new AlbumDao_Impl(this);
        }
        return _albumDao;
      }
    }
  }
}

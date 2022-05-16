package com.example.flo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Song::class, User::class], version = 1)
abstract class SongDatabase: RoomDatabase() {
    abstract fun songDao() : SongDao
    abstract fun userDao() : UserDao

    companion object{
        private var instance :SongDatabase? = null

        @Synchronized
        fun getInstance(context: Context): SongDatabase? {
            if (instance == null) {
                synchronized(SongDatabase::class) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        SongDatabase::class.java,
                        "song-database" //다른 데이터베이스랑 이름겹치면 꼬임
                    ).allowMainThreadQueries().build()
                    //근데 효율적인 앱을 위해선 main스레드에 일을 넘기면 안돼
                }
            }
            return instance
        }
    }
}
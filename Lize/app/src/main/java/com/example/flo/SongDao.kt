package com.example.flo

import androidx.room.*

@Dao
interface SongDao {
    @Insert
    fun insert(song: Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    @Query("SELECT * FROM SongTable") //SongTable로부터(FROM) 모든 값을(*) 가져와라(SELECT)
    fun getSongs(): List<Song>

    @Query("SELECT * FROM SongTable WHERE id = :id") //id값이 넘겨받은 id값인 song을 가져와라(WHERE : 조건문)
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike= :isLike WHERE id = :id")
    fun updateIsLikebyId(isLike: Boolean, id: Int)

    //좋아요가 클릭된 애들만 addSongs
    @Query("SELECT * FROM SongTable WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}
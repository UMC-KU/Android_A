package com.example.flo

import androidx.room.*

@Dao
interface SongDao {
    @Insert
    fun insert(song:Song)

    @Update
    fun update(song: Song)

    @Delete
    fun delete(song: Song)

    //원하는기능 생기면 쿼리문 통해 추가해주기
    @Query("SELECT * FROM songTable") //송테이블의 모든 데이터를 가져와라
    fun getSongs(): List<Song>

    @Query("SELECT * FROM songTable WHERE id = :id")  //where를 조건문처럼 활용,
    fun getSong(id: Int): Song

    @Query("UPDATE SongTable SET isLike = :isLike WHERE id = :id")
    fun updateIsLikeById(isLike: Boolean, id:Int)

    @Query("SELECT * FROM songTable WHERE isLike = :isLike")
    fun getLikedSongs(isLike: Boolean): List<Song>
}
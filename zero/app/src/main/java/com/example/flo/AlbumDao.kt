package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album: Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums():List<Album>

    @Insert
    fun likeAlbum(like:Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND album =:albumId")
    fun isLikedAlbum(userId:Int, albumId: Int): Int? //nullalbe

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND album =:albumId")
    fun disLikedAlbum(userId:Int, albumId: Int) //nullalbe

    @Query("SELECT AT. * FROM liketable as LT LEFT JOIN AlbumTable as AT ON LT.album = AT.id WHERE LT.userId = :userId")
    //liketable as LT : LT라고 쓰겠다
    //LEFT JOIN: 왼쪽에 있는 LIKETABLE을 기준으로 JOIN해주겠다.
    fun getLikedAlbums(userId: Int): List<Album>
}
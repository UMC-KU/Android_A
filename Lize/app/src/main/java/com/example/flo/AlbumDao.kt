package com.example.flo

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface AlbumDao {

    @Insert
    fun insert(album:Album)

    @Query("SELECT * FROM AlbumTable")
    fun getAlbums():List<Album>

    //사용자가 좋아요를 누른 앨범 추가
    @Insert
    fun likeAlbum(like:Like)

    @Query("SELECT id FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    //AlbumFragment에 들어갈 때, 사용자가 좋아요를 눌렀는 지 누르지 않았는 지 확인
    fun isLikedAlbum(userId: Int, albumId : Int) : Int?
    //정보가 없다면 null을 반환하므로 "?"처리

    @Query("DELETE FROM LikeTable WHERE userId = :userId AND albumId = :albumId")
    //AlbumFragment에 들어갈 때, 사용자가 좋아요를 취소할 경우
    fun disLikedAlbum(userId: Int, albumId : Int)

    //LikeTable as LT : LikeTable=LT
    //LikeTable as LT LEFT JOIN AlbumTable as AT : 왼쪽의 LT를 기준으로 오른쪽의 AT를 join
    //on LT.albumId = AT.id : albumId가 같은 것끼리 join
    @Query("SELECT AT.* FROM LikeTable as LT LEFT JOIN AlbumTable as AT on LT.albumId = AT.id WHERE LT.userId = :userId")
    //좋아요한 앨범을 가져오기
    fun getLikedAlbums(userId:Int):List<Album>
}
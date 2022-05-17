package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

//라이크 테이블 생성
@Entity(tableName = "LikeTable")
data class Like(
    var userId :Int,
    var album: Int
){
    @PrimaryKey(autoGenerate = true) var id:Int =0
}

//앨범다오를 만들어서 앨번테이블과 함께 사용할꺼야.
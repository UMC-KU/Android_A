package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

//데이터 클래스
//제목, 가수, 사진 , 재생시간, 현재 재생시간, isPlaying(재생되고 있는지)
@Entity(tableName = "SongTable")
data class Song(
    val title : String? = "",
    val singer : String = "",
    var second :Int=0,
    var playTime: Int =0,
    var isPlaying: Boolean =false ,
    var music: String = "", // 어떤 음악이 재생되고 있는지를 알려주는 변수
    var coverImg: Int? = null,
    var isLike:Boolean = false
){
    @PrimaryKey(autoGenerate = true) var id: Int=0
}

package com.example.flo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "AlbumTable")
data class Album(
    @PrimaryKey(autoGenerate = false) var id : Int =0,
    //타이틀, 가수, 표지이미지
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    //var song: ArrayList<Song>? = null //수록곡
)

package com.example.flo

data class Album(
    //타이틀, 가수, 표지이미지
    var title: String? = "",
    var singer: String? = "",
    var coverImg: Int? = null,
    var song: ArrayList<Song>? = null //수록곡
)

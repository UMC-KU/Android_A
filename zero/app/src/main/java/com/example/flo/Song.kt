package com.example.flo

//데이터 클래스
data class Song(
    val title : String = "",
    val singer : String = "",
    var second :Int=0,
    var playTime: Int =0,
    var isPlayig: Boolean =false
)

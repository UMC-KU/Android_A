package com.example.flo

data class Song(
    val title : String = "",
    val singer : String = "",
    var second : Int = 0, //몇 초 인지
    var playTime : Int =0, //노래가 얼마나 재생이 되었나
    var isPlaying : Boolean = false //노래가 재생중인가
)

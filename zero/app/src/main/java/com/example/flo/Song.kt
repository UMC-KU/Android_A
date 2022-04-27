package com.example.flo

//데이터 클래스
data class Song(
    val title : String? = "",
    val singer : String = "",
    var second :Int=0,
    var playTime: Int =0,
    var isPlaying: Boolean =false ,
    var music: String = "" // 어떤 음악이 재생되고 있는지를 알려주는 변수
)

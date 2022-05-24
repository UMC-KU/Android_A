package com.example.flo

import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName(value = "inSuccess") val inSuccess:Boolean,
    @SerializedName(value = "code") val code:Int,
    @SerializedName(value = "message") val message:String,
    @SerializedName(value = "result") val result: Result? //밑의 result타입의 객체로 응답값을 받아올 수 있다.

    //유의할점이 회원가입 api와 로그인 api를 같은 데이터 클래스로 응답값을 받고 있다.
    //따라서 result에 null처리를 해야 회원가입 api를 사용했을 때 알아서 null처리가 되기 때문에 같이 사용할 수 있다.
    )

data class Result(
    @SerializedName(value = "userIdx") var userIdx : Int,
    @SerializedName(value = "jwt") var jwt : String
)


package com.example.flo

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthRetrofitInterface {
    @POST("/users")
    fun signUp(@Body user:User): Call<AuthResponse> //우리가 response받을 값을 미리 생성해줘야해
    //call정의를 하면 받는 쪽에서,  enqueue라는 메소드 정의할 수 있고 거기서 응답을 처리함.


    @POST("/users/login")
    fun login(@Body user:User): Call<AuthResponse>
    //레트로핏 객체 만들어주기

}
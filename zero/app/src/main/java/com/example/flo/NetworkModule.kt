package com.example.flo

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://edu-api-test.softsquared.com" //COM주소뒤에 /붙어있지 않아 만약 여기 붙어있다면 INTERFACE에서는 /빼줘야해

//이렇게 NetWorkModule이라는 파일에 따로 함수를 적는 이유는 여러 화면에서 공통적으로 쓰이는 함수이기 때문에 이렇게 만든다.
fun getRetrofit(): Retrofit{
    val retrofit = Retrofit.Builder().baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create()).build()

    return retrofit
}
package com.example.flo

import android.util.Log
import android.view.View
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthService {
    private lateinit var signUpView: SignUpView

    fun setSignUpView(signUpView: SignUpView){
        this.signUpView = signUpView
    }

    //api를 호출하고 관리하는 메서드
    fun signUp(user:User){
        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(user).enqueue(object : Callback<AuthResponse> {

            //응답이 왔을 때 처리하는 부분
           override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                // 서버개발자가 보낸 응답값을 파싱하기 위해서 RESPONSE안에서 BODY값을 가져와야해요.
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000->signUpView.onSignUpSuccess() //성공시
                    else->signUpView.onSignUpFailure() //의도한 실패?
                }

            }

            //네트워크 연결 자체가 실패했을 때, 실행하는 부분
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        //함수가 잘 수행되었는 지 확인 위해 - 비동기식이라;;
        Log.d("SIGNUP", "HELLO")
    }
}
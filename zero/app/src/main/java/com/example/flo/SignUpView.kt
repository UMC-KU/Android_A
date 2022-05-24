package com.example.flo

interface SignUpView { //액티비티와 auth서비스를 연결시켜주기 위한 것
    fun onSignUpSuccess()
    fun onSignUpFailure()
}
package com.example.flo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo.databinding.ActivitySignUpBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입-가입완료버튼
        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            finish() //SignUpActivity꺼짐 => finish를 통해 다시 로그인 화면으로 이동
        }
    }

    //사용자가 입력한 값을 가져오는 함수
    private fun getUser(): User {
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()

        return User(email,pwd)
    }

    //회원가입 진행하는 함수
    private fun signUp(){
        //입력해야하는 칸이 다 입력안되었거나, 비밀번호랑 비밀번호확인칸이 같지 않으면 회원가입 불가능
        //=>이작업은 밸리데이션 처리(?)라고한다.
        if (binding.signUpIdEt.text.toString().isEmpty()||binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return //함수 작동끝
        }

        if(binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return //함수의 작동끝
        }
        // 이 과정을 넘겼다면 회원가입이 무사히 진행됨.

        // 정보저장 작업
        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser()) //getUser(): 지금 적혀있는 user가 입력한 정보라고 생각하면 좋다.

        //로그를 통해 확인해보자
        val user = userDB.userDao().getUsers()
        Log.d("SIGNUPACT",user.toString())
    }
}
package com.example.flo

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySignupBinding

class SignUpActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //가입완료 버튼을 누르면 회원가입 끝
        binding.signUpSignUpBtn.setOnClickListener{
            signUp()
            finish() //회원가입액티비티 끄고 로그인 화면으로 이동
        }

    }

    //회원가입
    //사용자가 입력한 값 가져오기
    private fun getUser() : User{
        //이메일, 비밀번호, 비밀번호 확인 : EditText (사용자가 입력가능) -> 일반 Text를 가져올 때랑 동일하게 사용
        //EditText값을 가져왔다면, String으로 사용할 경우 반드시 toString()처리 해줄것
        val email : String = binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd : String = binding.signUpPasswordEt.text.toString()

        return User(email, pwd)
    }

    //실제 회원가입 진행
    private fun signUp(){
        //이메일 형식이 잘못된 경우
        if(binding.signUpIdEt.text.toString().isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return
        }
        //비밀번호와 비밀번호 확인이 일치하지 않는 경우
        if(binding.signUpPasswordEt.text.toString().isEmpty() != binding.signUpPasswordCheckEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return
        }

        //정상적으로 입력된 경우
        val userDB = SongDatabase.getInstance(this)!!
        userDB.userDao().insert(getUser())

    }
}
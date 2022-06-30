package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity(){
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입버튼 누르면 activity_login.xml -> activity_signup.xml로 이동
        binding.loginSignUpTv.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        //로그인 버튼을 누르면 아래 코드 실행
        binding.loginSignInBtn.setOnClickListener{
            login()
        }
    }

    //로그인
    private fun login(){
        //이메일이 입력되지 않은 경우
        if(binding.loginIdEt.text.toString().isEmpty() || binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }
        //비밀번호가 입력되지 않은 경우
        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        //정상적으로 입력된 경우
        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()

        //사용자가 입력한 이메일과 패스워드 정보가 회원가입된 정보인지 확인
        val songDB = SongDatabase.getInstance(this)!!
        val user = songDB.userDao().getUser(email, pwd)

        //user가 null이 아닐 경우 : DB에서 해당 유저를 찾았을 경우
        user?.let{
            Log.d("LOGIN_ACT/GET_USER", "userId : ${user.id}, &user")
            saveJwt(user.id)
            startMainActivity()
        }

        //user가 null일 경우
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    //JWT를 저장하는 함수
    private fun saveJwt(jwt: Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()

        editor.putInt("jwt", jwt)
        editor.apply()
    }

    //로그인이 끝나면 MainActivity로 전환
    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding:ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.loginSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        binding.loginSignInBtn.setOnClickListener {
            login()
        }
    }

    //로그인함수
    private fun login(){
        //입력하지 않은 부분이 있는지 밸리데이션 체크 필요
        if (binding.loginIdEt.text.toString().isEmpty()||binding.loginDirectInputEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
            return //함수 작동끝
        }
        if(binding.loginPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            return //함수의 작동끝
        }

        val email : String = binding.loginIdEt.text.toString() + "@" + binding.loginDirectInputEt.text.toString()
        val pwd : String = binding.loginPasswordEt.text.toString()
        //사용자가 입력한 이메일과 password의 정보가 db에 존재하는지 확인 필요
        //확인하기 위해서 songbd부터 연결시켜줄께

        val songDB = SongDatabase.getInstance(this)!!
        val user = songDB.userDao().getUsers(email,pwd)

        user?.let { //렛문안에 있는 코드가 수행되는 경우, db에서 해당유저를 찾았을 경우이다.
            Log.d("LOGIN_ACT/GET_USER","userId : ${user.id}, $user")
            saveJwt(user.id)
            //로그인 다 하면, 메인화면으로 넘어가기
            startMainActivity()
        }
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    //jwt를 저장할수있는함수 <= 이거 왜 만든다고??
    private fun saveJwt(jwt:Int){
        //sharedpreference는 또 뭐더라.
        val spf =getSharedPreferences("auth", MODE_PRIVATE)
        //에디터는 또 뭐더라 - sharedprf의 짝꿍인가?
        val editor = spf.edit()

        editor.putInt("jwt",jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
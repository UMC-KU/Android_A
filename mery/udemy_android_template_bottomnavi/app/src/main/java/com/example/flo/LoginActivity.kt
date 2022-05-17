package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.flo.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.logInSignUpTv.setOnClickListener {
            startActivity(Intent(this, SignUpActivity::class.java))
        }
        binding.logInLogInTv.setOnClickListener{
            login()
        }
    }

    private fun login(){
        if (binding.logInIdEt.text.toString().isEmpty() || binding.logInEmailEt.text.toString().isEmpty()){
            Toast.makeText(this, "이메일을 입력해주세요.", Toast.LENGTH_SHORT).show()
            return
        }

        if (binding.logInPasswordEt.text.toString().isEmpty()){
            Toast.makeText(this, "비밀번호를 입력해 주세요", Toast.LENGTH_SHORT).show()
            return
        }

        val email = binding.logInIdEt.text.toString() + "@" + binding.logInEmailEt.text.toString()
        val pwd = binding.logInPasswordEt.text.toString()
        val songDB = SongDatabase.getInstance(this)!!
        val user = songDB.userDao().getUser(email, pwd)


        user?.let{
            Log.d("LOGIN_ACT/GET_USER", "userId: ${user.id}, $user")
            saveJWT(user.id)
            startMainActivity()
        }
        Toast.makeText(this, "회원 정보가 존재하지 않습니다.", Toast.LENGTH_SHORT).show()
    }

    private fun saveJWT(jwt:Int){
        val spf = getSharedPreferences("auth", MODE_PRIVATE)
        val editor = spf.edit()
        editor.putInt("jwt", jwt)
        editor.apply()
    }

    private fun startMainActivity(){
        val intent =Intent(this, MainActivity::class.java)
        startActivity(intent)
    }

}
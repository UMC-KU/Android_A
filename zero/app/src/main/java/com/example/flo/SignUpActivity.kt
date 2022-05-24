package com.example.flo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.flo.databinding.ActivitySignUpBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignUpActivity : AppCompatActivity() , SignUpView{
    lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //회원가입-가입완료버튼
        binding.signUpSignUpBtn.setOnClickListener {
            signUp()
            //finish() //SignUpActivity꺼짐 => finish를 통해 다시 로그인 화면으로 이동
        }
    }

    //사용자가 입력한 값을 가져오는 함수
    private fun getUser(): User {
        val email: String =
            binding.signUpIdEt.text.toString() + "@" + binding.signUpDirectInputEt.text.toString()
        val pwd: String = binding.signUpPasswordEt.text.toString()
        val name: String = binding.signUpNameEt.text.toString()

        return User(email, pwd, name)
    }

    //회원가입 진행하는 함수
    /*   private fun signUp(){
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
    }*/

    //이제 roomDB가 아니라 네트워크를 통한 회원가입!
    private fun signUp() {
        //밸리데이션 싱크
        //입력해야하는 칸이 다 입력안되었거나, 비밀번호랑 비밀번호확인칸이 같지 않으면 회원가입 불가능
        //=>이작업은 밸리데이션 처리(?)라고한다.
        if (binding.signUpIdEt.text.toString()
                .isEmpty() || binding.signUpDirectInputEt.text.toString().isEmpty()
        ) {
            Toast.makeText(this, "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return //함수 작동끝
        }

        if (binding.signUpNameEt.text.toString().isEmpty()) {
            Toast.makeText(this, "이름 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show()
            return //함수 작동끝
        }

        if (binding.signUpPasswordEt.text.toString() != binding.signUpPasswordCheckEt.text.toString()) {
            Toast.makeText(this, "비밀번호가 일치하지 않습니다.", Toast.LENGTH_SHORT).show()
            return //함수의 작동끝
        }
        // 이 과정을 넘겼다면 회원가입이 무사히 진행됨.

        //이제 roomDB가 아니라 네트워크를 통해 유저정보를 저장해주려한다!
        //서비스와 관련된 인터페이스가 필요
        //레트로픽 객체와 서비스 객체 만들어주자
/*        val authService = getRetrofit().create(AuthRetrofitInterface::class.java)
        authService.signUp(getUser()).enqueue(object : Callback<AuthResponse> {

            //응답이 왔을 때 처리하는 부분
           override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                Log.d("SIGNUP/SUCCESS", response.toString())
                // 서버개발자가 보낸 응답값을 파싱하기 위해서 RESPONSE안에서 BODY값을 가져와야해요.
                val resp: AuthResponse = response.body()!!
                when(resp.code){
                    1000->finish()//성공시
                    2016,2018->{
                        //이메일과 관련된 오류들 처리
                        binding.signUpEmailErrorTv.visibility= View.VISIBLE
                        binding.signUpEmailErrorTv.text = resp.message
                    }
                }
            }

            //네트워크 연결 자체가 실패했을 때, 실행하는 부분
            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                Log.d("SIGNUP/FAILURE", t.message.toString())
            }

        })
        //함수가 잘 수행되었는 지 확인 위해 - 비동기식이라;;
        Log.d("SIGNUP", "HELLO")*/

        val authService = AuthService()
        authService.setSignUpView(this)

        authService.signUp(getUser())
    }

    //코드의 개수를 줄이고, 각각 관리하기 위해 모듈화 시킴
    override fun onSignUpSuccess() {
        finish()
    }

    override fun onSignUpFailure() {
        TODO("Not yet implemented")
    }
}

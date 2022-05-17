package com.example.flo

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentLockerBinding
import com.google.android.material.tabs.TabLayoutMediator

class LockerFragment : Fragment() {

    lateinit var binding: FragmentLockerBinding
    private val information = arrayListOf("저장한 곡", "음악파일", "저장앨범")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerBinding.inflate(inflater, container, false)

        val lockerAdapter = LockerVPAdapter(this)
        binding.lockerContentVp.adapter = lockerAdapter
        TabLayoutMediator(binding.lockerContentTb, binding.lockerContentVp){
            tab,position ->
            tab.text = information[position]
        }.attach()

        binding.lockerLoginTv.setOnClickListener {
            startActivity(Intent(activity, LoginActivity::class.java))
            //로그인버튼 누르면 로그인액티비티로 화면 전환
        }
        return binding.root
    }

    override fun onStart() {
        super.onStart()
        initViews()
    }
    //로그인, 로그아웃 <= 로그인, 로그아웃을 해야하는 상황인지 판단 필요
    //현재 sharedprf에 jwt값이 저장되어 있는지 확인하기 위해
    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        //activity?를 적어주는 이유는 fragment에서 사용할때 적는 방법이라고 생각해주기
        return spf!!.getInt("jwt",0) //spf에서 가져온 값이 없다면 0을 반환해줘라는 의미
    }

    //뷰 초기화시켜주는 함수
    private fun initViews() {
        val jwt:Int= getJwt()
        if(jwt == 0){
            //로그인을 해야하는 상황
            binding.lockerLoginTv.text="로그인"
            binding.lockerLoginTv.setOnClickListener {
                startActivity(Intent(activity,LoginActivity::class.java)) //로그인화면
            }
        }else{
            binding.lockerLoginTv.text="로그아웃"
            binding.lockerLoginTv.setOnClickListener {
                //로그아웃 진행
                logout()
                startActivity(Intent(activity,MainActivity::class.java)) //메인화면
            }
        }
    }

    private fun logout(){
        //jwt를 0으로 만들어주기
        val spf = activity?.getSharedPreferences("auth",AppCompatActivity.MODE_PRIVATE)
        val editor = spf!!.edit()
        editor.remove("jwt") //jwt라는 키값에 저장된 값을 없애준다.
        editor.apply()
    }
}
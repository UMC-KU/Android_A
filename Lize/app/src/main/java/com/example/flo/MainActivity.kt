package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //이전 Android Studio : find view by id -> activitymain.xml에 있는 id와 class를 연결
    //사용방법 : 하나의 변수 - 하나의 view 연결
    //val textView = findViewById<TextView>(R.id.main_player.cl) : R-Resource
    //P : 변수를 너무 많이 생성해야함, null처리x 등
    //S : viewbinding
    lateinit var binding: ActivityMainBinding //binding선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //Song (data class)에 데이터 불러오고 저장
        val song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString())

        //binding을 사용해서 view의 id값을 가져오는 방법 : binding.viewid.~
        binding.mainPlayerCl.setOnClickListener{
            //mainPlayerlayout을 클릭했을 때, Activity 전환
            //startActivity(Intent(어디서, 어디로 이동)) : Intent - 하나의 액티비티에서 사용하는 택배상자
            //startActivity메소드를 통해 Intent(택배상자)를 this->SongActivity로 보냄
            //방법1.
            //startActivity(Intent(this, SongActivity::class.java))

            //방법2. SongActivity에서 택배상자를 받아주는 작업 필요
            val song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString())
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            startActivity(intent)

        }
        initBottomNavigation()

        //Log메소드를 사용하면 Logcat에서 값 확인 가능
        Log.d("Song", song.title + song.singer)

    }

    //fragment를 전환해 각각의 화면을 보여줌
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> { //homeFragment를 눌렀을 때
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment()) //homeFragment보여지기
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }

                R.id.lookFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LookFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.searchFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, SearchFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
                R.id.lockerFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, LockerFragment())
                        .commitAllowingStateLoss()
                    return@setOnItemSelectedListener true
                }
            }
            false
        }
    }
}
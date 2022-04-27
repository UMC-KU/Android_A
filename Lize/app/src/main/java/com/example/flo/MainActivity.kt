package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {
    //이전 Android Studio : find view by id -> activitymain.xml에 있는 id와 class를 연결
    //사용방법 : 하나의 변수 - 하나의 view 연결
    //val textView = findViewById<TextView>(R.id.main_player.cl) : R-Resource
    //P : 변수를 너무 많이 생성해야함, null처리x 등
    //S : viewbinding
    lateinit var binding: ActivityMainBinding //binding선언
    
    private var song:Song = Song()
    private var gson:Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)

        binding = ActivityMainBinding.inflate(layoutInflater) //binding 초기화
        setContentView(binding.root)

        //** sharedPreferences에 저장된 값을 불러올 것이기 때문에 더 이상 필요없는 변수 **
        //Song (data class)에 데이터 불러오고 저장
        //val song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString(), 0, 60, false, "music_lilac")

        //binding을 사용해서 view의 id값을 가져오는 방법 : binding.viewid.~
        binding.mainPlayerCl.setOnClickListener{
            //mainPlayerlayout을 클릭했을 때, Activity 전환
            //startActivity(Intent(어디서, 어디로 이동)) : Intent - 하나의 액티비티에서 사용하는 택배상자
            //startActivity메소드를 통해 Intent(택배상자)를 this->SongActivity로 보냄
            //방법1.
            //startActivity(Intent(this, SongActivity::class.java))

            //방법2. SongActivity에서 택배상자를 받아주는 작업 필요
            val intent = Intent(this, SongActivity::class.java)
            intent.putExtra("title", song.title)
            intent.putExtra("singer", song.singer)
            intent.putExtra("second", song.second)
            intent.putExtra("playTime", song.playTime)
            intent.putExtra("isPlaying", song.isPlaying)
            intent.putExtra("music", song.music)
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

    private fun setMiniPlayer(song: Song){
        binding.mainMiniplayerTitleTv.text = song.title
        binding.mainMiniplayerSingerTv.text = song.singer
        binding.songMiniprogressSb.progress = (song.second*100000)/song.playTime
    }

    //액티비티 전환 시, onStart()부터 실행
    //onResume()에서 실행해도 되지만, onResume()은 이미 액티비티가 실행되고 나서 실행 -> onStart()에서 미리 UI를 구성하는 것이 안정적
    override fun onStart() {
        super.onStart()
        //song data반영
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //sharedPreferences의 이름 : song
        val songJson = sharedPreferences.getString("songData", null) //sharedPreferences의 안에 저장된 데이터의 이름 : songData
        
        //가져온 데이터를 song객체에 담기
        song = if(songJson ==null){ //데이터가 존재하지 않을 때, 직접 데이터 지정
            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
        }else{ //데이터가 존재하면 저장되어있는 데이터 불러오기
            gson.fromJson(songJson, Song::class.java) //Json->java
        }

        setMiniPlayer(song)

    }
}
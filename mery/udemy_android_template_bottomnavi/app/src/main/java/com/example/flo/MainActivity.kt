package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    private var song = Song()
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        inputDummySongs()
        initBottomNavigation()

        //sharedpreferences에 저장된 값을 가져올 거라 이제 필요없음
        //val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false, "song")

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId",song.id)
            editor.apply()

            val intent=Intent(this, SongActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onStart() { //액티비티 전환될때 onStart에서 시작
        super.onStart()
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
//        val songJson = sharedPreferences.getString("songData", null)
//
//        song = if(songJson == null){
//            Song("라일락", "아이유(IU)", 0, 60, false, "song")
//        }else{
//            gson.fromJson(songJson, Song::class.java)
//        }
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        val songDB = SongDatabase.getInstance(this)!!
        song=if(songId==0){
            songDB.songDao().getSong(1)
        }else
        {
            songDB.songDao().getSong(songId)
        }

        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)
    }


    private fun initBottomNavigation(){            //네비게이션 구현부분

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {

                R.id.homeFragment -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_frm, HomeFragment())
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
        binding.songProgress.progress = (song.second*100000)/song.playTime //seekbar max가 10만이라 10만이라고 설정
    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs() //데이터를 songs에 받아줌

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "Lilac",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
                )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유 (IU)",
                0,
                200,
                false,
                "music_flu",
                R.drawable.img_album_exp2,
                false,
                )
        )


        songDB.songDao().insert(
            Song(
                "Butter",
                "방탄소년단 (BTS)",
                0,
                190,
                false,
                "music_butter",
                R.drawable.img_album_exp,
                false,
                )
        )

        songDB.songDao().insert(
            Song(
                "Next Level",
                "에스파 (AESPA)",
                0,
                210,
                false,
                "music_next",
                R.drawable.img_album_exp3,
                false,
                )
        )



        songDB.songDao().insert(
            Song(
                "Boy with Luv",
                "music_boy",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp4,
                false,
                )
        )

        songDB.songDao().insert(
            Song(
                "BBoom BBoom",
                "모모랜드 (MOMOLAND)",
                0,
                240,
                false,
                "music_bboom",
                R.drawable.img_album_exp5,
                false,
                )
        )

        //데이터 잘 들어갔는지 확인하기 위해 다시 데이터 받음
        val _songs=songDB.songDao().getSongs()
        //로그를 통해 데이터 확인
        Log.d("DB data", _songs.toString())

    }
}
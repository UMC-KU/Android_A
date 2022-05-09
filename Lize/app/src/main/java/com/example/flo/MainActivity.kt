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
    //Song 초기화 방법 : SharedPreference를 통해서 id를 받아옴 -> onStart() 방법2.
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
//            val intent = Intent(this, SongActivity::class.java)
//            intent.putExtra("title", song.title)
//            intent.putExtra("singer", song.singer)
//            intent.putExtra("second", song.second)
//            intent.putExtra("playTime", song.playTime)
//            intent.putExtra("isPlaying", song.isPlaying)
//            intent.putExtra("music", song.music)
//            startActivity(intent)

            //방법3. Data를 넘길 때 intent가 아니라 song의 Id를 넘겨줌
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this, SongActivity::class.java)
            startActivity(intent)

        }

        //노래 DB에 넣기
        inputDummySongs()

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
        //방법1.
//        //song data반영
//        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //sharedPreferences의 이름 : song
//        val songJson = sharedPreferences.getString("songData", null) //sharedPreferences의 안에 저장된 데이터의 이름 : songData
//
//        //가져온 데이터를 song객체에 담기
//        song = if(songJson ==null){ //데이터가 존재하지 않을 때, 직접 데이터 지정
//            Song("라일락", "아이유(IU)", 0, 60, false, "music_lilac")
//        }else{ //데이터가 존재하면 저장되어있는 데이터 불러오기
//            gson.fromJson(songJson, Song::class.java) //Json->java
//        }

        //방법2.
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)
        
        //DB에서 해당 id에 해당하는 song가져오기
        val songDB = SongDatabase.getInstance(this)!!

        song = if(songId == 0){ //songId가 없다면,
            songDB.songDao().getSong(1) //디폴트로 1번 노래 가져오고
        }else{ //있다면,
            songDB.songDao().getSong(songId) //해당 Id의 노래 가져오기
        }

        Log.d("song ID", song.id.toString())

        setMiniPlayer(song)

    }

    //데이터가 DB에 없다면 DB에 넣어주기
    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        val songs = songDB.songDao().getSongs()

        //데이터가 원래 있었다면 그냥 함수 종료
        if(songs.isNotEmpty()) return

        //없는 데이터라면 insert
        songDB.songDao().insert(
            Song(
                "LILAC",
                "아이유 (IU)",
                0,
                230,
                false,
                "music_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "COIN",
                "아이유 (IU)",
                0,
                230,
                false,
                "music_coin",
                R.drawable.img_album_exp2,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "SAVAGE",
                "에스파 (asepa)",
                0,
                230,
                false,
                "music_savage",
                R.drawable.img_album_exp3,
                false,
            )
        )

        songDB.songDao().insert(
            Song(
                "WEEKEND",
                "태연 (taeyeon)",
                0,
                230,
                false,
                "music_weekend",
                R.drawable.img_album_exp,
                false,
            )
        )

        //데이터가 잘 들어갔는지 check용
        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
}
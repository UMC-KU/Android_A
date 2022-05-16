package com.example.flo

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.flo.databinding.ActivityMainBinding
import com.google.gson.Gson

class MainActivity : AppCompatActivity() {

    //전역변수
    lateinit var binding: ActivityMainBinding

    private var song:Song = Song() //초기화해줘야해 sharedPreference를 통해 id받아와야해
    private var gson: Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_FLO)//다시 원래의 테마로 돌려줌.
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



        //val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString(),0,60,false,"iu_lilac")
        //우린 이제 sharedpreference로 부터 값을 가져올꺼니까 필요 없다.

        binding.mainPlayerCl.setOnClickListener {
            //startActivity(Intent(this, SongActivity::class.java))
            //메인 플레이어를 눌렀을때 메인 액티비티에서 송 액티비티로 액티비티 전환을 시켜준다.

           /* //다른방법-인텐트(=택배상자)이용
            //상자안에 데이터를 담아서 보낸다
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            intent.putExtra("second",song.second)
            intent.putExtra("playTime",song.playTime)
            intent.putExtra("isPlaying",song.isPlaying)
            intent.putExtra("music",song.music)
            startActivity(intent)
            //상자는 songActivity에서 작성해보겠습니다.*/

            //7주차 - 데이터를 넘겨줄때 intent로 넘겨주는게 아니라 song의 id를 저장시켜주면 된다
            val editor = getSharedPreferences("song", MODE_PRIVATE).edit()
            editor.putInt("songId", song.id)
            editor.apply()

            val intent = Intent(this,SongActivity::class.java)
            startActivity(intent)
        }
        initBottomNavigation()
        inputDummySongs()

        //미니플레이어 관련 실습
        //val song = Song(binding.mainMiniplayerTitleTv.text.toString(), binding.mainMiniplayerSingerTv.text.toString())

        //로그: 데이터가 잘 오는지, 잘 저장이 되었는지 확인할 수 있는 ..!
        //Logcat이라는 창
        Log.d("Song",song.title+song.singer)
        //테그를 통해 잘 담겼는지 확인 가능
        //=>데이터 클래스에 제목과 가수가 제대로 잘 담김을 확인 가능

    }

    //바텀네비게이션
    private fun initBottomNavigation(){

        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, HomeFragment())
            .commitAllowingStateLoss()

        binding.mainBnv.setOnItemSelectedListener{ item ->
            when (item.itemId) {
                //각각의 id를 이용해서 각각의 프레그먼트로 전환가능
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

    private fun setMiniPlayer(song:Song){
        binding.mainMiniplayerTitleTv.text=song.title
        binding.mainMiniplayerSingerTv.text=song.singer
        binding.mainProgressSb.progress = (song.second*100000)/song.playTime
        //여기서 second*십만을 해준 이유는 우리가 만든 sb의 max가 10만이라서
    }

    override fun onStart() {
        super.onStart()
/*        //onCreate가 아닌 onStart에서 해주는 이유는 액티비티 전환이 될때 onStart부터 실행되기 때문
        //=>song액티비티의 데이터를 반영해주기 위해서 onStart에서 해주는게 좋다.
        //그럼 onResume은?? onStart가 사용자에게 보여주기 직전이고, onResume은 사용자에게 보여진 후이기 때문에
        //onStart에서 UI를 초기화 시켜주는게 더욱 안정적.
        super.onStart()
        //SHAREDPREFERENCE에 저장되어있는 값 가져오기
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val songJson = sharedPreferences.getString("songData",null)

        //가져온 값을 song객체에 담아주기
        song = if(songJson==null){ //최초에 데이터가 없을때, song데이터를 직접지정해줌
            Song("라일락", "아이유(IU)",0,60,false,"iu_lilac")
        }else{ //데이터가 존재할땐 직접 가져오기
            gson.fromJson(songJson, Song::class.java)
            //json=>song으로 변환
        }*/

        //7주차
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)
        //db에서 해당id에 해당하는 song 가져와야해 => dao에서 작업
        val songDB = SongDatabase.getInstance(this)!!

        song= if(songId == 0){
            songDB.songDao().getSong(1)
        }else{
            songDB.songDao().getSong(songId)
        }

        //로그로 받아온 송의 id확인 + 데이터 렌더링 작업
        Log.d("song ID", song.id.toString())
        setMiniPlayer(song)

    }

    private fun inputDummySongs(){
        val songDB = SongDatabase.getInstance(this)!!
        //songDB에 데이터 들어있는지 확인하고자함 => 가지고 오는 구문 songDao의 쿼리문으로 구현
        val songs = songDB.songDao().getSongs()

        if(songs.isNotEmpty()) return

        songDB.songDao().insert(
            Song(
                "LILAC",
                "아이유(IU)",
                0,
                240,
                false,
                "iu_lilac",
                R.drawable.img_album_exp2,
                false,
            )
        )



        songDB.songDao().insert(
            Song(
                "Weekend",
                "태연(Taeyeon)",
                0,
                240,
                false,
                "taeyeon_weekend",
                R.drawable.img_album_exp6,
                false
            )
        )

        songDB.songDao().insert(
            Song(
                "Flu",
                "아이유(IU)",
                0,
                240,
                false,
                "iu_flu",
                R.drawable.img_album_exp2,
                false
            )
        )

        val _songs = songDB.songDao().getSongs()
        Log.d("DB data", _songs.toString())
    }
}
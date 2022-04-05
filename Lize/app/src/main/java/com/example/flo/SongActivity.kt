package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

//Java 상속 extends vs Kotlin 상속 :(콜론)
class SongActivity : AppCompatActivity() { //AppCompatActivity : 안드로이드에서 activity를 사용할 수 있도록 만들어놓은 클래스

    //viewBinding 사용
    //1. Gradle Scripts->build.gradle (Module)에 viewBinding추가
    //2. 아래와 같이 사용
    //변수 선언 방법
    //var : 변수 선언 후 나중에 값 변경 가능 vs val : 나중에 값 변경 가능x
    //var 변수명 : 타입 = 초기화 값
    lateinit var binding : ActivitySongBinding //lateinit 전방 선언(java) : 나중에 초기화
    //MainActivity에서는 ActivityMainBinding타입 지정 : layout->activity_main.xml 불러오기
    lateinit var song : Song //Song (data class)의 값을 초기화시키기 위한 변수
    lateinit var timer : Timer //Timer

    //activity생성 시 처음으로 실행되는 함수 : onCreate
    //override : 상속받아 변경 / fun : function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) //binding변수 초기화
        //Inflater : xml에 표기된 layout를 memory에 객체화 시키기
        setContentView(binding.root) //setContentView(xml id) : xml에 있는 걸 가져와서 마음대로 쓰기

        initSong()
        setPlayer(song)

        //SongActivity에서 down버튼 누르면 MainActivity로 이동 = SongActivity끄기
        binding.songDownIb.setOnClickListener{
            finish() //현 화면 끄기
        }

        //play버튼을 누르면 play->pause버튼으로 전환
        binding.songPlayIv.setOnClickListener{
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(true)
        }

        //like버튼을 누르면 off->on으로 전환
        binding.songLikeOffIv.setOnClickListener{
            setLikeStatus(false)
        }
        binding.songLikeOnIv.setOnClickListener{
            setLikeStatus(true)
        }

        //Unlike버튼을 누르면 off->on으로 전환
        binding.songUnlikeOffIv.setOnClickListener{
            setUnlikeStatus(false)
        }
        binding.songUnlikeOnIv.setOnClickListener{
            setUnlikeStatus(true)
        }

        Log.d("test","왜 안됨 개짱나")
    }

    //Thread가 꺼질 때 자동으로 호출
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt() //Thread 종료
    }

    private fun initSong(){
        if(intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song : Song){ //SongActivity화면을 받아와서 초기화된 song에 대한 데이터를 view rendering

        binding.songTitleTv.text = intent.getStringExtra("title")
        binding.songSingerTv.text = intent.getStringExtra("singer")
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime : Int, var isPlaying:Boolean = true) : Thread(){
        private var second : Int = 0
        private var mills : Float = 0f

        override fun run() {
            super.run()
            try {
                while(true){ //Timer는 계속 돌아가야 하므로 무한반복
                    if(second>=playTime){ //노래가 모두 재생되면 반복문 종료
                        break
                    }
                    if(isPlaying){
                        sleep(50)
                        mills+=50

                        runOnUiThread{
                            binding.songProgressSb.progress = ((mills/playTime)*100).toInt()
                        }
                        //진행시간 표시 timer
                        if(mills % 1000 == 0f){
                            runOnUiThread{
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second/60, second%60)
                            }
                            second++
                        }
                    }
                }

            }catch (e: InterruptedException){
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }


        }
    }

    //함수정의방법 : fun 함수이름(인자){내용}
    fun setPlayerStatus(isPlaying : Boolean){

        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPlayIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }else{
            binding.songPlayIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun setLikeStatus(isLike : Boolean){
        if(isLike){
            binding.songLikeOffIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility = View.GONE
        }else{
            binding.songLikeOffIv.visibility = View.GONE
            binding.songLikeOnIv.visibility = View.VISIBLE
        }
    }

    fun setUnlikeStatus(isUnlike : Boolean){
        if(isUnlike){
            binding.songUnlikeOffIv.visibility = View.VISIBLE
            binding.songUnlikeOnIv.visibility = View.GONE
        }else{
            binding.songUnlikeOffIv.visibility = View.GONE
            binding.songUnlikeOnIv.visibility = View.VISIBLE
        }
    }
}
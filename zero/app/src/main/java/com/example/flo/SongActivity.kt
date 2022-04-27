package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

class SongActivity : AppCompatActivity() {

    //전역변수
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer
    private var mediaPlayer: MediaPlayer? = null //?는  nullable 널 값이 들어올 수 있다.
    //nullable해준 이유는 액티비티가 소멸될때 미디어 플레이어를 해제시켜줘야하기 때문.
    private var gson : Gson = Gson()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //액티비티가 생성될때 처음으로 실행되는 함수
        //inflate: xml에 표기된 레이아웃들을 메모리에 객체화시키는 행동
        binding = ActivitySongBinding.inflate(layoutInflater) //바인딩을 초기화.
        setContentView(binding.root)//xml에 있는걸 가져와서 마음대로 쓸꺼야
        //root(xml의 최상단 파일)를 통해 모든애들 가져옴

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener{
            //현재 액티비티 꺼주기
            finish() // song이 mainact위로 띄워지고, finish하면 없어짐.
        }

        //재생/일시정지부분
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true) //재생버튼을 눌렀으니까
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false) //일시정지버튼을 눌렀으니까
        }

        //좋아요부분
        binding.songLikeOffIv.setOnClickListener {
            setLikeStatus(false)
        }
        binding.songLikeOnIv.setOnClickListener {
            setLikeStatus(true)
        }

        //안좋아요부분
        binding.songUnlikeOffIv.setOnClickListener {
            setUnlikeStatus(false)
        }
        binding.songUnlikeOnIv.setOnClickListener {
            setUnlikeStatus(true)
        }

        //화면과(?) 데이터 전달!
        //받는 사람 입장에선 상자가 올 수도, 안 올수도 있잖아. if문 통해 처리해줄께
        if(intent.hasExtra("title")&&intent.hasExtra("singer")){//title과 singer라는 키(?)값을 갖는 것이 인텐트에 있는지 확인
            //이 뜻은 텍스트뷰의 텍스트를 바꿔줄껀데, 인텐트라는 택배상자에서 타이틀이라는 키 값을 가진 스트링으로 바꿔줄꺼야라는 의미
            binding.songMusicTitleTv.text=intent.getStringExtra("title")!!
            binding.songSingerNameTv.text=intent.getStringExtra("singer")!!
        }


    }


    //main이랑 song 액티비티가 서로 songdata클래스를 통해 주고 받고 있다.
    private fun initSong(){//Songdata클래스를 초기화시켜주는 함수?
        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second",0),
                intent.getIntExtra("playTime",0),
                intent.getBooleanExtra("isPlaying",false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song:Song){ //songActivity화면을 받아와서, 초기화된 song에 대한 data정보를 뷰렌더링?해주겠습니다.
        binding.songMusicTitleTv.text=intent.getStringExtra("title")
        binding.songSingerNameTv.text=intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d",song.second / 60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d",song.playTime / 60, song.playTime%60)
        binding.songProgressSb.progress =(song.second*1000/song.playTime)
        //지금 songdata클래스에는 mp3파일 이름이 string으로 되어 있어,
        // 이걸 실제로 실행시킬려면, 리소스파일에서 해당 string값으로 찾아서 리소스를 반환해주는게 필요
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer =MediaPlayer.create(this, music)//미디어 플레이어에게 어떤 음악을 재생할지 알려줌.
        setPlayerStatus(song.isPlayig)
    }

    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlayig = isPlaying
        timer.isPlaying =isPlaying

        if (isPlaying){ //재생중이면
            //재생버튼은 안보이게됨 , 정지버튼 보이게됨???
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
            mediaPlayer?.start()
        }
        else{ //재생중이 아니면
            //재생버튼은 보이게 , 정지버튼 안 보이게됨???
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
            if(mediaPlayer?.isPlaying ==true){ //미디어플레이어가 재생중이 아닐때 pause를 하면 오류가 생길 수도 있어서
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer(){
        timer = Timer(song.playTime, song.isPlayig)
        timer.start()
    }

    //이너 클래스 클래스 내부의 클래스, binding변수를 사용해야하므로
    inner class Timer(private val playTime : Int, var isPlaying: Boolean = true):Thread(){

        private var second : Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try{
                while (true){

                    if(second >= playTime){
                        break;
                    }

                    if(isPlaying){ //반복될때마다 50 밀스씩 슬립시켜서 지연되는 시간을 관리해주겠습니다.
                        sleep(50)
                        mills+=50;
                        //progressbar도 증가해야해

                        //seekbar적용
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills/ playTime) * 100 ).toInt()
                        }

                        //1000밀스=1초
                        if(mills % 1000 ==0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d",second / 60, second%60)
                            }
                            second++
                        }
                    }
                }
            }catch(e:InterruptedException){
                Log.d("Song","쓰레드가 죽었습니다. ${e.message}")
            }

        }
    }

    //사용자가 포커스를 잃었을때 음악이 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        //1000으로 나눠주는 이유는 밀리세컨드 단위를 초단위로 바꿔주기 위해

        //***************
        //song이 어플이 종료되어도 남아있으려면 어딘가에 저장되어 있어야해 =>sharedPreferences이용
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //이름, 모드(PRIVATE-자기 앱에서만 사용가능)
        //셰어드프리퍼런스: 내부저장소에 데이터를 저장할 수 있게 해주는 것 <= 앱이 종료됬다 실행돼도, 저장된 데이터를 꺼내서 사용할 수 있게 해줌.
        //간단한 설정값과 같은 데이터를 저장할때 매우 중요
        //중요하거나 무거운 데이터를 저장할땐, 깁?이나 서버, 파일의 형태로 저장하겠지만
        //로그인할때 비번기억 같은 간단한 값은 SHAREDPREFERENCE가 유용

        //***************************
        //데이터 저장할때 에디터 사용해야해
        val editor = sharedPreferences.edit() // 에디터
        //인텐트에서 했던 것 처럼 put을 통해 에디터에 넣어주면 돼.
//        editor.putString("title", song.title)
//        editor.putString("title", song.title),,,,
        //근데 하나하나 넣어주기 귀찮 => 제이슨 포맷으로 배달을 시켜서 데이터를 한번에 넣어줄꺼야
        //제이슨? 일종의 데이터 표현 표준 (보통 자바 객체를 다른 곳으로 전송할때 제이슨 포맷이용 )
        //제이슨은 매우 중요한 개념이지 제이슨이 어떤 포맷으로 되어있는지 찾아보기
        //***************
        //song을 제이슨으로 바꿔주기
        //자바객체 <=> 제이슨 : 지슨 (라이브러리 추가 필요 )
        val songJson = gson.toJson(song) //송객체를 제이슨 포맷으로 바꿔줌
        editor.putString("song", songJson)

        editor.apply() // 어플라이까지 해줘야지 실제 저장공간에 저장된다.

    }


    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        //불필요한 리소스 낭비 방지용
        mediaPlayer?.release() // 미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }


    fun setLikeStatus(isOn : Boolean){
        if (isOn){
            binding.songLikeOffIv.visibility = View.VISIBLE
            binding.songLikeOnIv.visibility=View.GONE
            Toast.makeText(this,"좋아요 한 곡이 취소되었습니다.",Toast.LENGTH_SHORT).show()
        }
        else{ //좋아요 안 눌러진 상태에서 클릭
            binding.songLikeOffIv.visibility = View.GONE
            binding.songLikeOnIv.visibility=View.VISIBLE
            Toast.makeText(this,"좋아요 한 곡에 담겼습니다.",Toast.LENGTH_SHORT).show()
        }
    }

    fun setUnlikeStatus(isOn : Boolean){
        if (isOn){
            binding.songUnlikeOffIv.visibility = View.VISIBLE
            binding.songUnlikeOnIv.visibility=View.GONE
        }
        else{ //안좋아요 안 눌러진 상태에서 클릭
            binding.songUnlikeOffIv.visibility = View.GONE
            binding.songUnlikeOnIv.visibility=View.VISIBLE
        }
    }

}
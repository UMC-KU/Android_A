package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

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
    //lateinit var song : Song //Song (data class)의 값을 초기화시키기 위한 변수
    lateinit var timer : Timer //Timer

    //음악 재생
    private var mediaPlayer: MediaPlayer? = null
    
    //Gson사용
    private var gson: Gson = Gson()

    //노래 관리 (기존의 song대신)
    val songs = arrayListOf<Song>()
    //DB
    lateinit var songDB: SongDatabase
    //initSong
    var nowPos = 0

    //activity생성 시 처음으로 실행되는 함수 : onCreate
    //override : 상속받아 변경 / fun : function
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater) //binding변수 초기화
        //Inflater : xml에 표기된 layout를 memory에 객체화 시키기
        setContentView(binding.root) //setContentView(xml id) : xml에 있는 걸 가져와서 마음대로 쓰기

        initPlayList()
        initSong()
        //setPlayer(song) -> InitSong에서 호출

        initClickListener()
        

        Log.d("test","왜 안됨 개짱나")
    }

    //리스트에 있는 노래를 불러와서 songs에 저장
    private fun initPlayList(){
        songDB  = SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }
    
    //clickListener 모음집
    private fun initClickListener(){
        //SongActivity에서 down버튼 누르면 MainActivity로 이동 = SongActivity끄기
        binding.songDownIb.setOnClickListener{
            finish() //현 화면 끄기
        }

        //play버튼을 누르면 play->pause버튼으로 전환
        binding.songPlayIv.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener{
            setPlayerStatus(false)
        }

        //좋아요 버튼 기능 수행
        binding.songLikeOffIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }

        //Unlike버튼을 누르면 off->on으로 전환
        binding.songUnlikeOffIv.setOnClickListener{
            setUnlikeStatus(false)
        }
        binding.songUnlikeOnIv.setOnClickListener{
            setUnlikeStatus(true)
        }
        
        //다음곡으로 넘어가기
        binding.songNextIv.setOnClickListener{
            moveSong(1)
        }
        
        //이전곡으로 넘어가기
        binding.songPreviousIv.setOnClickListener{
            moveSong(-1)
        }
    }

    //처음 보여질 음악 초기화
    private fun initSong(){
        //방법1. intent사용
//        if(intent.hasExtra("title") && intent.hasExtra("singer")){
//            song = Song(
//                intent.getStringExtra("title")!!,
//                intent.getStringExtra("singer")!!,
//                intent.getIntExtra("second", 0),
//                intent.getIntExtra("playTime", 0),
//                intent.getBooleanExtra("isPlaying", false),
//                intent.getStringExtra("music")!!
//            )
//        }

        //방법2. songs에서 index를 통해 관리 -> nowPos사용
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId", 0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID", songs[nowPos].id.toString())

        startTimer()
        setPlayer(songs[nowPos])
    }

    //좋아요 기능 수행
    private fun setLike(isLike: Boolean){
        songs[nowPos].isLike = !isLike //아직 DB의 값 업데이트X
        songDB.songDao().updateIsLikebyId(!isLike, songs[nowPos].id)

        if(!isLike){
            binding.songLikeOnIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
    
    //노래 이동하기
    private fun moveSong(direct: Int){
        if(nowPos + direct < 0){
            Toast.makeText(this, "first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this, "last song", Toast.LENGTH_SHORT).show()
            return 
        }
        
        nowPos += direct
        
        //새로운 노래 시작
        timer.interrupt()
        startTimer()
        
        mediaPlayer?.release()
        mediaPlayer = null
        
        setPlayer(songs[nowPos])
    }

    //넘겨받은 songId값과 같은 노래 인덱스 찾기 in songs
    private fun getPlayingSongPosition(songId: Int): Int{
        for(i in 0 until songs.size){
            if(songs[i].id == songId){
                return i
            }
        }
        return 0
    }

    private fun setPlayer(song : Song){ //SongActivity화면을 받아와서 초기화된 song에 대한 데이터를 view rendering

        //방법1.
//        binding.songTitleTv.text = intent.getStringExtra("title")
//        binding.songSingerTv.text = intent.getStringExtra("singer")
        //방법2.
        binding.songTitleTv.text = song.title
        binding.songSingerTv.text = song.singer

        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second/60, song.second%60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime/60, song.playTime%60)
        binding.songAlbumImgIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)



        
        //현재 재생되는 노래의 정보를 string값으로 가지고 있음 -> res폴더에서 해당 string값을 가진 res을 반환하는 과정이 필요
        //resources.getIdentifier(string값, 폴더, this.packageName) 사용
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        //반환받은 res를 mediaPlayer에게 물려줌
        mediaPlayer = MediaPlayer.create(this, music) //이 음악을 재생할거야!라고 알려주기

        //songdata에 따라 좋아요버튼 표시
        if(song.isLike){
            binding.songLikeOnIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }

        setPlayerStatus(song.isPlaying)
    }

    private fun startTimer(){
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
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

    //사용자가 포커스를 잃었을 때, 음악 중지
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        //앱을 종료했다가 다시 돌아왔을 때, 이전에 재생되고 있던 song데이터를 적용
        //음악이 몇 초 동안 재생되고 있었는가? (밀리세컨드->세컨드 : /1000)
        songs[nowPos].second = ((binding.songProgressSb.progress * songs[nowPos].playTime)/100)/1000
        //앱이 종료되어도 정보를 기억하고 있어야함 -> sharedPreferences 이용 (내부저장소에 데이터 저장)

        //데이터 저장 시
        //중요한 데이터, 무거운 데이터 : DB, 서버, 파일의 형태
        //간단한 데이터 : sharedPreferences ex)비밀번호 기억
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        //"song" : sharedPreferences의 이름
        //MODE_PRIVATE : PRIVATE하게 사용할 것(본인의 앱에서만 사용)

        //sharedPreferences를 사용하기 위해선 반드시 editor를 이용
        val editor = sharedPreferences.edit()
        //방법1. Song의 데이터개수만큼 put작업 반복
//        editor.putString("title", song.title) //intent하듯 사용
//        editor.putString("singer", song.singer) //...
        //방법2. 방법1은 코드가 너무 길어지므로 Json 포맷으로 객체를 한번에 넘기기 (Gson이용)
//        val songJson = gson.toJson(song) //song객체를 Json포맷으로 변경
//        editor.putString("songData", songJson)
        //방법3. song이 아니라 songId를 보내주기
        editor.putInt("songId", songs[nowPos].id)

        
        editor.apply() //반드시 apply()해야 실제 저장작업 완료
//      apply() : git에서의 commit과 push를 담당

    }

    //Thread가 꺼질 때 자동으로 호출
    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt() //Thread 종료
        mediaPlayer?.release() //mediaPlayer가 갖고 있던 리소스 해제 : 불필요한 공간 사용X
        mediaPlayer = null //mediaPlayer 해제
    }

    //함수정의방법 : fun 함수이름(인자){내용}
    fun setPlayerStatus(isPlaying : Boolean){

        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying){
            binding.songPlayIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start() //음악 재생
        }else{
            binding.songPlayIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            //재생중이 아닐 때, pause를 하면 오류가 생길 수도 있으므로 if문 추가
            if(mediaPlayer?.isPlaying == true){ //음악 중지
                mediaPlayer?.pause()
            }
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
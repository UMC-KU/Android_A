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
    lateinit var binding: ActivitySongBinding
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null //액티비티 선언될때 미디어플레이어 해제
    private var gson: Gson = Gson()

    val songs = arrayListOf<Song>()
    lateinit var songDB: SongDatabase
    var nowPos = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initPlayList()
        initSong()
        initClickListener()

    }

    // 사용자가 포커스를 잃었을 때 음악이 중지
    override fun onPause() {
        super.onPause()
        songs[nowPos].second=((binding.songProgressSb.progress*songs[nowPos].playTime)/100)/1000
        songs[nowPos].isPlaying=false
        setPlayerStatus(false)

        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE) //자기앱에서만 사용가능하게
        val editor = sharedPreferences.edit() //에디터

        //json은 데이터 표준
        //짓슨 java객체를 json으로 json객체를 java로, 라이브러리 추가
//        val songJson = gson.toJson(songs[nowPos])
//        editor.putString("songData", songJson)

        //songid값 저장
        editor.putInt("songId",songs[nowPos].id)
        editor.apply()//얘까지 해줘야 실제 공간에 저장이 됨
    }



    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release() //미디어 플레이어가 갖고 있던 리소스 해제
        mediaPlayer = null //미디어 플레이어 해제
    }


    private fun initPlayList(){
        songDB=SongDatabase.getInstance(this)!!
        songs.addAll(songDB.songDao().getSongs())
    }

    private fun initClickListener(){
        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }

        binding.songNextIv.setOnClickListener{
            moveSong(+1)
        }

        binding.songPreviousIv.setOnClickListener{
            moveSong(-1)
        }

        binding.songLikeIv.setOnClickListener{
            setLike(songs[nowPos].isLike)
        }
    }
    private fun initSong() {
        //id를 통해 songs와 비교해 index값 가져옴
        val spf = getSharedPreferences("song", MODE_PRIVATE)
        val songId = spf.getInt("songId",0)

        nowPos = getPlayingSongPosition(songId)

        Log.d("now Song ID",songs[nowPos].id.toString())
        startTimer()
        setPlayer(songs[nowPos])
    }

    private fun setLike(isLike:Boolean){
        songs[nowPos].isLike=!isLike
        songDB.songDao().updateIsLikeById(!isLike,songs[nowPos].id)

        if(!isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }
    private fun moveSong(direct:Int){
        if(nowPos + direct < 0){
            Toast.makeText(this,"first song", Toast.LENGTH_SHORT).show()
            return
        }
        if(nowPos + direct >= songs.size){
            Toast.makeText(this,"last song", Toast.LENGTH_SHORT).show()
            return
        }

        nowPos+=direct

        timer.interrupt()
        startTimer()

        mediaPlayer?.release()
        mediaPlayer=null

        setPlayer(songs[nowPos])
    }
    private fun getPlayingSongPosition(songId: Int):Int{
        for (i in 0 until songs.size){
            if(songs[i].id==songId){
                return i
            }
        }
        return 0

    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = song.title
        binding.songSingerNameTv.text = song.singer
        binding.songStartTimeTv.text =
            String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text =
            String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songAlbumIv.setImageResource(song.coverImg!!)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)

        if(song.isLike){
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.songLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
        setPlayerStatus(song.isPlaying)
    }

    fun setPlayerStatus(isPlaying: Boolean) {
        songs[nowPos].isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        } else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if (mediaPlayer?.isPlaying == true) {  //미디어플레이어는 재생중이 아닌데 pause하면 오류날 수 있어서 추가
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer() {
        timer = Timer(songs[nowPos].playTime, songs[nowPos].isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true) : Thread() {

        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {


                    if (second >= playTime) {
                        break
                    }

                    if (isPlaying) {
                        sleep(50)
                        mills += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime) * 100).toInt()

                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.songStartTimeTv.text =
                                    String.format("%02d:%02d", second / 60, second % 60)

                            }
                            second++
                        }
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")

            }
        }
    }


}
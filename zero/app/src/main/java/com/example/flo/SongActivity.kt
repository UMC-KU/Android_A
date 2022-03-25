package com.example.flo

import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding : ActivitySongBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //액티비티가 생성될때 처음으로 실행되는 함수
        binding = ActivitySongBinding.inflate(layoutInflater) //바인딩을 초기화.
        setContentView(binding.root)//xml에 있는걸 가져와서 마음대로 쓸꺼야
        binding.songDownIb.setOnClickListener{
            //현재 액티비티 꺼주기
            finish() // song이 mainact위로 띄워지고, finish하면 없어짐.
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true) //재생
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false) //일시정지
        }
    }

    fun setPlayerStatus(isPlaying : Boolean){
        if (isPlaying){ //재생중이면
            //재생버튼은 안보이게됨 , 정지버튼 보이게됨
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility=View.VISIBLE
        }
        else{ //재생중이 아니면
            //재생버튼은 보이게 , 정지버튼 안 보이게됨
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility=View.GONE
        }
    }
}
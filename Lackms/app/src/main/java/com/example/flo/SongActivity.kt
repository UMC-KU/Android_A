package com.example.flo

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity(){
    lateinit var binding : ActivitySongBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.songEndActivityIb.setOnClickListener{
            finish()
        }
        binding.songPauseSongIb.setOnClickListener{
            setPlayerStatus(true)
        }
        binding.songPauseSong2Ib.setOnClickListener{
            setPlayerStatus(false)
        }

        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
            binding.songSingTitleTv.text=intent.getStringExtra("title")
            binding.songSingerTv.text=intent.getStringExtra("singer")
        }
    }

    fun setPlayerStatus(isPlaying: Boolean){
        if(isPlaying){
            binding.songPauseSongIb.visibility= View.GONE
            binding.songPauseSong2Ib.visibility=View.VISIBLE
        }
        else{
            binding.songPauseSong2Ib.visibility= View.GONE
            binding.songPauseSongIb.visibility=View.VISIBLE
        }
    }
}
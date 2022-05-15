package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentSongBinding

class SongFragment : Fragment(){

    lateinit var binding: FragmentSongBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater, container, false)

        //toast message 띄우기
        binding.songLilacLayout.setOnClickListener{
            Toast.makeText(activity,"LILAC", Toast.LENGTH_SHORT).show()
        }

        //play버튼을 누르면 play->pause버튼으로 전환
        binding.albumMixToggleOffIv.setOnClickListener{
            setMixStatus(false)
        }
        binding.albumMixToggleOnIv.setOnClickListener{
            setMixStatus(true)
        }

        return binding.root
    }

    //내취향MIX버튼
    fun setMixStatus(isMix : Boolean){
        if(isMix){
            binding.albumMixToggleOffIv.visibility = View.VISIBLE
            binding.albumMixToggleOnIv.visibility = View.GONE
        }else{
            binding.albumMixToggleOffIv.visibility = View.GONE
            binding.albumMixToggleOnIv.visibility = View.VISIBLE
        }
    }
}
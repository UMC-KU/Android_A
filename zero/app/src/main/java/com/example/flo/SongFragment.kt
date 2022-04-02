package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentDetailBinding
import com.example.flo.databinding.FragmentSongBinding

class SongFragment:Fragment() {

    lateinit var binding: FragmentSongBinding //바인딩선언

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSongBinding.inflate(inflater,container,false)

        binding.albumMixToggleOffIv.setOnClickListener {
            setToggleStatus(false)
        }
        binding.albumMixToggleOnIv.setOnClickListener {
            setToggleStatus(true)
        }
        return binding.root
    }

    fun setToggleStatus(isOn:Boolean){
        if(isOn){
            binding.albumMixToggleOffIv.visibility = View.VISIBLE
            binding.albumMixToggleOnIv.visibility= View.GONE
        }
        else{ //버튼이 활성화가 안됐을때 클릭을 하면,
            binding.albumMixToggleOffIv.visibility = View.GONE
            binding.albumMixToggleOnIv.visibility=View.VISIBLE
        }
    }

}
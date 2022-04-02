package com.example.flo

import android.graphics.Color
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

        //취향믹스 버튼
        binding.albumMixToggleOffIv.setOnClickListener {
            setToggleStatus(false)
        }
        binding.albumMixToggleOnIv.setOnClickListener {
            setToggleStatus(true)
        }

        //전체선택버튼
        binding.songBtnSelectOffAllIv.setOnClickListener {
            setSelectButtonStatus(false)
        }
        binding.songBtnSelectOnAllIv.setOnClickListener {
            setSelectButtonStatus(true)
        }

        //전체선택 텍스트 클릭했을때도 변화주고싶음..!

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

    fun setSelectButtonStatus(isOn:Boolean){
        if(isOn){
            binding.songBtnSelectOffAllIv.visibility = View.VISIBLE
            binding.songBtnSelectOnAllIv.visibility= View.GONE
            binding.songChooseAllTv.setTextColor(Color.GRAY)
            binding.songChooseAllTv.setText("전체선택")

        }
        else{ //버튼이 활성화가 안됐을때 클릭을 하면,
            binding.songBtnSelectOffAllIv.visibility = View.GONE
            binding.songBtnSelectOnAllIv.visibility=View.VISIBLE
            binding.songChooseAllTv.setTextColor(Color.parseColor("#3F3FFF"))
            binding.songChooseAllTv.setText("선택해제")
        }
    }

}
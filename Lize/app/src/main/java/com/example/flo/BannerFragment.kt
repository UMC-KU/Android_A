package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding


class BannerFragment(val imgRes : Int) : Fragment() {
    //새로운 이미지를 넣을 수 있도록 인자로 imgRes로 받음
    lateinit var binding : FragmentBannerBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(inflater, container, false) //binding초기화

        //인자 값으로 받은 이미지로 imageview의 src값이 변경
        binding.bannerImageIv.setImageResource(imgRes)
        
        return binding.root
    }
}
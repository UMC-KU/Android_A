package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentBannerBinding

class BannerFragment(val imgRes : Int) : Fragment() {
    //바인딩선언 : 그러기위해선 xml파일을 만들어주도록 할께요
    lateinit var binding : FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBannerBinding.inflate(inflater, container, false)

        //인자값으로 받은 이미지로 이미지뷰의 src값이 변경됨.
        binding.bannerImageIv.setImageResource(imgRes)

        return binding.root
    }
}
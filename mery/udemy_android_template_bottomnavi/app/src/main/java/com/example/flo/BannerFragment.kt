package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainer
import com.example.flo.databinding.FragmentBannerBinding
import java.util.zip.Inflater


class BannerFragment(val imgRes : Int) : Fragment() {   //여러개의 인자를 넣음

    lateinit var  binding: FragmentBannerBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentBannerBinding.inflate(inflater, container, false)
        binding.bannerImageIv.setImageResource(imgRes)
        return binding.root
    }
}
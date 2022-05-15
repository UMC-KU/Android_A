package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.util.zip.Inflater

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var gson: Gson = Gson()

    //activity : onCreate vs fragment : onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //argument에서 데이터를 꺼내와서 binding
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)
        setInit(album) //binding작업 수행

        //fragment_album에서 back누르면 다시 HomeFragment로 이동
        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        //연결
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        //tablayout - viewpager 연결
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach() //tablayout과 viewpager붙이기 : attach
        //TabLayoutMediator : tablayout-viewpager2와 연결해주는 중재자
        //tablayout이 선택될 때 해당 viewpager의 위치를 동기화, viewpager 스크롤 시 tablayout 스크롤 위치 동기화
        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumImgIv.setImageResource(album.coverImg!!)
        binding.albumTitleMainTv.text = album.title.toString()
        binding.albumSingerMainTv.text = album.singer.toString()
    }



}
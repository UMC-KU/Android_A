package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment:Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 3 //수록곡, 상세정보, 영상 : 3개의 fragment필요

    override fun createFragment(position: Int): Fragment {
       return when(position){//when = switch문
           0-> SongFragment() //: 수록곡
           1-> DetailFragment() //: 상세정보
           else-> ViewFragment() //: 영상
       }
    }


}
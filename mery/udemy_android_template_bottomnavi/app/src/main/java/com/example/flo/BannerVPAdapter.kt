package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//FragmentStateAdapter 클래스를 상속받음
class BannerVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){

    private val fragmentlist : ArrayList<Fragment> = ArrayList() //초기화
    override fun getItemCount(): Int = fragmentlist.size

    override fun createFragment(position: Int): Fragment = fragmentlist[position]

    fun addFragment(fragment: Fragment){   //처음 실행할 때는 fragment에 아무것도 없음 -> homefragment에서 추가해주기 위해 씀
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) //새로운 값이 리스트에 추가되는 곳
    }
}
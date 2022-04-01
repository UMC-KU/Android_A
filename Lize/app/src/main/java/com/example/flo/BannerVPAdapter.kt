package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//adapter 인자 필요 : fragment조각 안에 이미지를 넣음
//adapter class : FragmentStateAdapter 클래스 상속
class BannerVPAdapter (fragemt: Fragment) : FragmentStateAdapter(fragemt) { //Q : fragment라고 치면 오류 : fragemt
    
    //여러 개의 fragment가 viewpager에서 보여져야함 -> fragment를 담아둘 공간이 필요
    private val fragmentlist : ArrayList<Fragment> = ArrayList() //초기화 필요
    //만약 private라고 선언하지 않을 경우 : 외부에서 데이터 변경 가능
    
    //implement members클릭 후 실행하기 위해 꼭 상속받아야하는 멤버 함수 추가
    override fun getItemCount(): Int { //class에서 연결된 viewpager에 data를 전달할 때, data를 몇 개를 전달할 거니?
       //fragmentlist에 담긴 fragment의 개수만큼 전달
        return fragmentlist.size //.add메소드를 통해 추가도 가능
    }
    //override fun getItemCount(): Int = fragmentlist.size로 간편하게 사용가능

    override fun createFragment(position: Int): Fragment = fragmentlist[position]
//    override fun createFragment(position: Int): Fragment { //fragmentlist안에 있는 item(fragment)를 생성
//        return fragmentlist[position] //0부터 getItemCount-1만큼 반환
//    }

    //처음실행될 때는 fragmentlist에 아무것도 없음 -> homefragment에서 추가해준 fragment를 사용하기 위한 함수
    fun addFragment(fragment:Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1) //list에 값이 추가될 때, viewpager에게 새로운 값이 추가되었다고 알려줌

    }

}
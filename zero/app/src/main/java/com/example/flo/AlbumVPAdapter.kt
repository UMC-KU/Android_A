package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AlbumVPAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun getItemCount(): Int = 3
    //수록곡, 상세정보,영상부분을 뷰페이저로 구성한다고 했다. => 즉 3개의 프레그먼트를 만들꺼다.


    //크리에이트 프레그먼트의 경우, 홈프레그먼트에서 배너를 구성할때는 add프레그먼트 함수를 이용해서
    //각각 똑같은 화면의 이미지만 바꿔서 프레그먼트를 만들어줌,
    //하지만 여기선 수록곡, 상세정보,영상의 프레그먼트가 반복된 화면이 아니라, 각각의 다른 뷰를 가지고 있기때문에
    // 프레그먼트를 각각 만들어줘서 연결하는 작업을 해보도록 할께

    override fun createFragment(position: Int): Fragment {
        return when(position){
            //각각의 프레그먼트 클래스와 레이아웃이 존재해야해.
            0-> SongFragment()
            1-> DetailFragment()
            else-> VideoFragment()
        }
    }
}
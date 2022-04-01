package com.example.flo

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

//어댑터는 인자를 써줘야해=> 왜냐하면 우리는 뷰페이저에 이미지를 넣어줘야하는데, 이미지를 그냥 넣어주는게 아니라,
// 프레그먼트라는 하나의 조각을 만들어서 각 프레그먼트 안에 이미지 하나를 넣어줘서 여러 프레그먼트가 좌우로 슬라이드 되도록 해야하기 때문이다

class BannerVPAdapter(fragment: Fragment) :FragmentStateAdapter(fragment) {
    //액티비티나 프레그먼트 클래스를 만들때 앱콤팩액티비티나 프레그먼트 클래스를 상속받은 것 처럼 마찬가지로
    //어댑터 클래스에서는 프레그먼트 스테이트 어댑터라는 걸 상속받을 꺼야.!!
    //프레그먼트 스테이트 어댑터: fragment라는 인자값을 가지고 있다.

    // ▼변수 선언: 왜? 여러가지의 프레그먼트들을 뷰페어져에서 보여줘야하는데 그러기 위해선 여러 프레그먼트를 담아둘 리스트가 필요하다.
    private val fragmentlist :ArrayList<Fragment> = ArrayList() //ArrayList()를 통해 초기화
    //private을 씀: 외부에서도 데이터변경이 일어나는 걸 방지하기 위해서 외부접근을 막음

/*    override fun getItemCount(): Int {
        return fragmentlist.size()
    }*/

    override fun getItemCount(): Int = fragmentlist.size
    //이 클래스에 연결된 뷰페이저한테 데이터를 전달할때 데이터를 몇개 전달해줄것이냐를써주는 함수

    override fun createFragment(position: Int): Fragment = fragmentlist[position] //0~getItemCount값 전까지
    //프래그먼트 리스트 안에 있는 아이템들, 즉 프레그먼트들을 생성해주는 함수.

    //처음시작할때는 fragmentlist엔 아무값도 xx => 홈프레그먼트에서 추가해줄 프레그먼트를 써주기 위해서(?) 사용함.
    fun addFragment(fragment: Fragment){
        fragmentlist.add(fragment)
        notifyItemInserted(fragmentlist.size-1)
        //뷰페이저에게 리스트에 새로운 값이 추가가 되었으니, 이것도 추가해서 보여줘라는 의미의 코드
    }


}
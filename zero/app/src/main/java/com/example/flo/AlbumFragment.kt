package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    //프래그먼트는 액티비티와 다르게 AppCompatActivity()를 상속받는게 아니라,Fragment() 상속
    lateinit var binding : com.example.flo.databinding.FragmentAlbumBinding//바인딩선언


    //액티비티의 온크리에이트 => 프레그먼트 온크리에이트뷰
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //프레그먼트에서 바인딩을 초기화해주는 방법
        binding = FragmentAlbumBinding.inflate(inflater,container,false) // 액티비티에서 사용한 레이아웃 인플레이터와 다른 패턴으로 이해하기

        //back눌렀을때, home으로 전환
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,HomeFragment()).commitAllowingStateLoss()
        }

        //binding.songLalacLayout
       return binding.root // fragment_album 뷰의 최상단과 연결되어 있음을 확인할 수 있다.
    }
}
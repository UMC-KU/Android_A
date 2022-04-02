package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    //프래그먼트는 액티비티와 다르게 AppCompatActivity()를 상속받는게 아니라,Fragment() 상속
    lateinit var binding : com.example.flo.databinding.FragmentAlbumBinding//바인딩선언

    //인포메이션이라는 어레이리스트 선언
    private val information = arrayListOf("수록곡", "상세정보", "영상")

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
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }

/*        binding.songLalacLayout.setOnClickListener {
            //toast메세지 띄우기
            Toast.makeText(activity,"LILAC",Toast.LENGTH_SHORT).show()
            //어디서 무엇을 얼마나 띄울것인지.
        }
        binding.songFloLayout.setOnClickListener {
            //toast메세지 띄우기
            Toast.makeText(activity,"FLO",Toast.LENGTH_SHORT).show()
            //어디서 무엇을 얼마나 띄울것인지.
        }
        binding.songCoinLayout.setOnClickListener {
            //toast메세지 띄우기
            Toast.makeText(activity,"COIN",Toast.LENGTH_SHORT).show()
            //어디서 무엇을 얼마나 띄울것인지.
        }*/

        val albumAdapter = AlbumVPAdapter(this) //앨범뷰페이저어댑터로 초기화
        binding.albumContentVp.adapter = albumAdapter

        //탭레이아웃과 뷰페이져 연결하는 중재자. 탭이 선택될때, 뷰페이저2의 위치를 선택된 탭과 동기화하고,
        //사용자가 뷰페이저2를 스크롤할때, 탭레이아웃의 스크롤 위치를 동기화합니다.
        // 인자: 연결할 텝레이아웃, 뷰페이저
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tab, position ->
            tab.text = information[position]//information어레이리스트 선언

        }.attach() //탭레이아웃과 뷰페이저를 붙여주는 내용

        //좋아요 버튼
        binding.albumLikeOffIv.setOnClickListener {
            binding.albumLikeOffIv.visibility = View.GONE
            binding.albumLikeOnIv.visibility= View.VISIBLE
            //Toast.makeText(activity,"좋아요 한 곡에 담겼습니다.",Toast.LENGTH_SHORT).show()
        }
        binding.albumLikeOnIv.setOnClickListener {
            binding.albumLikeOffIv.visibility = View.VISIBLE
            binding.albumLikeOnIv.visibility= View.GONE
            //Toast.makeText(activity,"좋아요 한 곡이 취소되었습니다.",Toast.LENGTH_SHORT).show()
        }

       return binding.root // fragment_album 뷰의 최상단과 연결되어 있음을 확인할 수 있다.
    }
}


package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

/*        binding.homeAlbumImgIv1.setOnClickListener {
            //액티비티에서는 startActivity메소드를 사용했지만 프레그먼트 전환은 다른 방식
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,AlbumFragment())
                .commitAllowingStateLoss()
        }*/

        //데이터 리스트 생성 더미 데이터
        albumDatas.apply{
            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
        }
        //실제 서비스에선 데이터를 서버에서 받아오게 된다. => 우린 서버가 없어서 데이터를 하나하나 만들어준것


        val bannerAdapter = BannerVPAdapter(this) //초기화
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp)) //가로 안: 추가할 프레그먼트
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))

        //뷰페이저와 어댑터를 연결
        binding.homeBannerVp.adapter = bannerAdapter
        //뷰페이저가 좌우로 스크롤 될 수 있도록 지정해주는 코드들
        binding.homeBannerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL

        return binding.root
    }
}
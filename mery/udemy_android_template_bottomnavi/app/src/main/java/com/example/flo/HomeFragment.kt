package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.flo.databinding.FragmentHomeBinding
import com.google.gson.Gson
import java.util.ArrayList

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding
    private var albumDatas = ArrayList<Album>()


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //binding.homeAlbumImgIv1.setOnClickListener {
        //    (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,AlbumFragment()).commitAllowingStateLoss()
        //}


        //데이터 리스트 생성 더미 데이터
        albumDatas.apply {
            add(Album("Butter", "방탄소년단 (BTS)",R.drawable.img_album_exp))
            add(Album("Lilac", "아이유 (IU)",R.drawable.img_album_exp2))
            add(Album("Next Level", "에스파 (AESPA)",R.drawable.img_album_exp3))
            add(Album("Boy with Luv", "방탄소년단 (BTS)",R.drawable.img_album_exp4))
            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)",R.drawable.img_album_exp))
            add(Album("Weekend", "태연 (Tae Yeon)",R.drawable.img_album_exp6))
        }

        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        //리사이클러뷰 연결해 어떤 데이터 사용할건지 알려줌
        binding.homeTodayMusicAlbumRv.adapter=albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)


        albumRVAdapter.setMyItemClickListener(object : AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                //앨범프래그먼트로 전환
                (context as MainActivity).supportFragmentManager.beginTransaction()
                    .replace(R.id.main_frm,AlbumFragment().apply {
                        arguments=Bundle().apply {
                            val gson = Gson()
                            val albumJson = gson.toJson(album)
                            putString("album", albumJson)
                        }
                    })
                .commitAllowingStateLoss()
            }
        })
        //Viewpager를 어떠한 선(adapter)을 연결하고 데이터란 전기를 얻어 동작한다고 생각
        val bannerAdapter = BannerVPAdapter(this)   //adapterclass 필요
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))  //추가할 fragment
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter=bannerAdapter
        binding.homeBannerVp.orientation=ViewPager2.ORIENTATION_HORIZONTAL //viewpage가 좌우로 스크롤 될 수 있도록

        return binding.root
    }
}

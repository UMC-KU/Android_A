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

class HomeFragment : Fragment() {

    lateinit var binding: FragmentHomeBinding

    private var albumDatas = ArrayList<Album>()

    private lateinit var songDB : SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        //오늘 발매 응악 : scrollview사용했을 경우
//        binding.homeAlbumImgIv1.setOnClickListener {
//            //fragment전환
//            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
//            //id : main_frm : activity_main에서 FrameLayout의 id
//        }

        //ArrayList에 데이터 추가 : 데이터 리스트 생성 더미 데이터
//        albumDatas.apply {
//            add(Album("Butter", "방탄소년단 (BTS)", R.drawable.img_album_exp))
//            add(Album("Lilac", "아이유 (IU)", R.drawable.img_album_exp2))
//            add(Album("Next Level", "에스파 (AESPA)", R.drawable.img_album_exp3))
//            add(Album("Boy with Luv", "방탄소년단 (BTS)", R.drawable.img_album_exp4))
//            add(Album("BBoom BBoom", "모모랜드 (MOMOLAND)", R.drawable.img_album_exp5))
//            add(Album("Weekend", "태연 (Tae Yeon)", R.drawable.img_album_exp6))
//        }
        //songDB에 추가
        songDB = SongDatabase.getInstance(requireContext())!! //songDB초기화
        albumDatas.addAll(songDB.albumDao().getAlbums())

        //Adapter-datalist를 연결
        val albumRVAdapter = AlbumRVAdapter(albumDatas)
        //Adapter-recyclerView를 연결
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        //layoutManager 설정 : 수평으로 나열된 LinearLayout사용 (Horizontal)
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        //Adapter에서 listener를 이용할 수 있도록 listener객체 던져주기
        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener{
            override fun onItemClick(album: Album) {
                //fragment전환
                //fragment에 album정보 넘겨줄 때 bundle사용

                //작성한 코드를 손쉽게 함수로 변경하는 법
                //코드 드래그 -> Refactor -> function -> 함수 이름 지정
                changeAlbumFragment(album)
                //id : main_frm : activity_main에서 FrameLayout의 id
            }

//            override fun onRemoveAlbum(position: Int) {
//                albumRVAdapter.removeItem(position)
//            }

        })

        //viewpager이용 방법
        //: 전기(Adapter)를 사용해서 전자제품(viewpage)사용하기
        //list안에 fragment추가
        val bannerAdapter= BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))//인자 : 추가할 fragment
        //viewpager와 adapter연결
        binding.homeBannerVp.adapter = bannerAdapter
        binding.homeBannerVp.orientation = ViewPager2.ORIENTATION_HORIZONTAL //viewpager 좌우 스크롤 가능

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm,
            AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }
}
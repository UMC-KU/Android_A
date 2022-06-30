package com.example.flo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.flo.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator
import com.google.gson.Gson
import java.util.zip.Inflater

class AlbumFragment : Fragment() {
    lateinit var binding : FragmentAlbumBinding

    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked : Boolean = false

    private var gson: Gson = Gson()

    //activity : onCreate vs fragment : onCreateView
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        //argument에서 데이터를 꺼내와서 binding
        val albumJson = arguments?.getString("album")
        val album = gson.fromJson(albumJson, Album::class.java)

        //isLiked 초기설정
        isLiked = isLikedAlbum(album.id)

        setInit(album) //binding작업 수행
        setOnClickListeners(album)

        //fragment_album에서 back누르면 다시 HomeFragment로 이동
        binding.albumBackIv.setOnClickListener{
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        //연결
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter

        //tablayout - viewpager 연결
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp){
            tab, position ->
            tab.text = information[position]
        }.attach() //tablayout과 viewpager붙이기 : attach
        //TabLayoutMediator : tablayout-viewpager2와 연결해주는 중재자
        //tablayout이 선택될 때 해당 viewpager의 위치를 동기화, viewpager 스크롤 시 tablayout 스크롤 위치 동기화
        return binding.root
    }

    private fun setInit(album: Album){
        binding.albumImgIv.setImageResource(album.coverImg!!)
        binding.albumTitleMainTv.text = album.title.toString()
        binding.albumSingerMainTv.text = album.singer.toString()

        //좋아요가 눌리면 하트 색 변경
        if(isLiked){
            binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    //현재 로그인된 유저정보 가져오기
    private fun getJwt():Int{
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        return spf!!.getInt("jwt", 0) //shardPreferences에서 가져온 값이 없다면 0반환
    }

    //좋아요를 눌렀을 때 DB에 저장
    private fun likeAlbum(userId:Int, albumId:Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId)
        //좋아요 눌렀을 때, LikeTable에 정보 추가
        songDB.albumDao().likeAlbum(like)
    }

    //Album에서 사용자의 좋아요여부를 알기위함
    private fun isLikedAlbum(albumId:Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        val likeId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId) //어떤 유저가 해당 앨범을 좋아요 눌렀는가

        return likeId != null //유저가 좋아요 눌렀다면 true / 좋아요X면 false
    }

    //사용자가 좋아요를 취소하면, LikeTable에서 해당 앨범 지우기
    private fun disLikedAlbum(albumId:Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()
        
        songDB.albumDao().disLikedAlbum(userId, albumId) //어떤 유저가 해당 앨범을 좋아요 눌렀는가

    }
    
    //좋아요를 눌렀을 때, 클릭이벤트 처리
    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeOffIv.setOnClickListener{
            if(isLiked){ //좋아요눌려있는 상태
                //좋아요 취소
                binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{ //좋아요가 눌려있지 않은 상태
                //좋아요
                binding.albumLikeOffIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }
    }



}
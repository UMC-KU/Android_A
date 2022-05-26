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

class AlbumFragment : Fragment() {
    //프래그먼트는 액티비티와 다르게 AppCompatActivity()를 상속받는게 아니라,Fragment() 상속
    lateinit var binding : com.example.flo.databinding.FragmentAlbumBinding//바인딩선언
    private var gson: Gson = Gson()
    //인포메이션이라는 어레이리스트 선언
    private val information = arrayListOf("수록곡", "상세정보", "영상")

    private var isLiked : Boolean = false

    //액티비티의 온크리에이트 => 프레그먼트 온크리에이트뷰
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //프레그먼트에서 바인딩을 초기화해주는 방법
        binding = FragmentAlbumBinding.inflate(inflater,container,false) // 액티비티에서 사용한 레이아웃 인플레이터와 다른 패턴으로 이해하기

        //Home에서 넘어온 데이터 받아오기
        val albumJson = arguments?.getString("album") //argument에서 데이터를 꺼내서
        val album = gson.fromJson(albumJson, Album::class.java) //자바객체로 변환
        isLiked = isLikedAlbum(album.id)
        setInit(album) //setInit함수를 만들어서 바인딩해줌
        setOnClickListeners(album)


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

/*        //좋아요 버튼
        binding.albumLikeOffIv.setOnClickListener {
            binding.albumLikeOffIv.visibility = View.GONE
            binding.albumLikeOnIv.visibility= View.VISIBLE
            //Toast.makeText(activity,"좋아요 한 곡에 담겼습니다.",Toast.LENGTH_SHORT).show()
        }
        binding.albumLikeOnIv.setOnClickListener {
            binding.albumLikeOffIv.visibility = View.VISIBLE
            binding.albumLikeOnIv.visibility= View.GONE
            //Toast.makeText(activity,"좋아요 한 곡이 취소되었습니다.",Toast.LENGTH_SHORT).show()
        }*/

       return binding.root // fragment_album 뷰의 최상단과 연결되어 있음을 확인할 수 있다.
    }

    private fun setInit(album: Album) { //뷰를 초기화해주는함수
        binding.albumAlbumIv.setImageResource(album.coverImg!!)
        binding.albumMusicTitleTv.text =album.title.toString()
        binding.albumSingerNameTv.text = album.singer.toString()
        if(isLiked){
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
        }else{
            binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
        }
    }

    private fun getJwt(): Int {
        val spf = activity?.getSharedPreferences("auth", AppCompatActivity.MODE_PRIVATE)
        //activity?를 적어주는 이유는 fragment에서 사용할때 적는 방법이라고 생각해주기
        return spf!!.getInt("jwt",0) //spf에서 가져온 값이 없다면 0을 반환해줘라는 의미
    }

    //앨범 좋아요 눌렀을때 db에 저장해주는 함수 2개 만들어주려한다.
    private fun likeAlbum(userId : Int, albumId: Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val like = Like(userId, albumId) // 앨범을 좋아요 눌렀을때 like테이블에 정보를 추가해주기 위해서다.

        songDB.albumDao().likeAlbum(like)
    }

    //사용자가 좋아요 눌렀는지 아닌지 확인위한 함수
    private fun isLikedAlbum(albumId:Int):Boolean{
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        val likedId : Int? = songDB.albumDao().isLikedAlbum(userId, albumId)
        return likedId !=null // 좋아요 했으면 true, 안했으면 false반환
    }

    private fun disLikedAlbum(albumId:Int){
        val songDB = SongDatabase.getInstance(requireContext())!!
        val userId = getJwt()

        songDB.albumDao().disLikedAlbum(userId, albumId)
    }

    //좋아요 눌렀을때 클릭되는걸 처리해주기 위해서
    private fun setOnClickListeners(album: Album){
        val userId = getJwt()
        binding.albumLikeIv.setOnClickListener {
            if(isLiked){
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_off)
                disLikedAlbum(album.id)
            }else{
                binding.albumLikeIv.setImageResource(R.drawable.ic_my_like_on)
                likeAlbum(userId, album.id)
            }
        }

        //back눌렀을때, home으로 전환
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main_frm,HomeFragment())
                .commitAllowingStateLoss()
        }



    }
}


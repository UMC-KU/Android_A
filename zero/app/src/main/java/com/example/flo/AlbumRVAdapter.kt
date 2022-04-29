package com.example.flo

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.flo.databinding.ItemAlbumBinding

class AlbumRVAdapter (private val albumList:ArrayList<Album>) : RecyclerView.Adapter<AlbumRVAdapter.ViewHolder>() {
    //어댑터가 아이템뷰 객체들에게 데이터를 바인딩해줄려면 매개변수로 데이터를 받아와야해

    interface MyItemClickListener{
        fun onItemClick(album:Album) //데이터 받아오기 위해 매개변수 추가
        fun onRemoveAlbum(position: Int)
    }

    private lateinit var mItemClickListener: MyItemClickListener
    fun setMyItemClickListener(itemClickListener: MyItemClickListener){
        mItemClickListener=itemClickListener
    }
    //외부에서 전달받을 수 있는 함수랑 전달받은 리스너 객체를 저장할 변수를 어댑터안에서 사용하기 위해 선언해줌.

    //뷰홀더를 생성해줘야할 때 호출됨
    //이곳에서 아이템 뷰 객체를 만든후, 뷰 홀더에 넣어준다
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): AlbumRVAdapter.ViewHolder {
        val binding: ItemAlbumBinding = ItemAlbumBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)

        return ViewHolder(binding)
    }

    //앨범프레그먼트로 전환되는 클릭이벤트 해주고 싶음.
    //onBindViewHolder가 포지션 값을 가지고 있기 떄문에 보통 클릭 이벤트는 여기서 해줌
    override fun onBindViewHolder(holder: AlbumRVAdapter.ViewHolder, position: Int) { //뷰홀더, 포지션(인덱스아이디)
        holder.bind(albumList[position])
        holder.itemView.setOnClickListener {
            //어댑터 외부에서 클릭리스너 구현해주고 싶다면
            mItemClickListener.onItemClick(albumList[position])}

        /*//이번엔 아이템뷰가 아니라 타이틀이 클릭되었을때로 해보자
        holder.binding.itemAlbumTitleTv.setOnClickListener{
            mItemClickListener.onRemoveAlbum(position)
        }*/
    }

    //데이터 추가
    fun addItem(album: Album){
        albumList.add(album)
        notifyDataSetChanged() //리사이클러뷰는 데이터가 바뀐걸 모르기 때문에 꼭 알려줘야한다.
    }

    //데이터 삭제
    fun removeItem(position: Int){
        albumList.removeAt(position)
        notifyDataSetChanged() //리사이클러뷰는 데이터가 바뀐걸 모르기 때문에 꼭 알려줘야한다.
    }

    override fun getItemCount(): Int = albumList.size
    //리사이클러뷰의 마지막이 언제인지 알게해줌

    //뷰 홀더 클래스 : 아이템뷰 객체들을 재활용하기 위해 날라가지 않도록 담고 있는 그릇
    inner class ViewHolder(val binding: ItemAlbumBinding): RecyclerView.ViewHolder(binding.root) {

        //받아온 데이터를 아이템 뷰 객체에 넣어줌.
        fun bind(album:Album){
            binding.itemAlbumTitleTv.text = album.title
            binding.itemAlbumSingerTv.text = album.singer
            binding.itemAlbumCoverImgIv.setImageResource(album.coverImg!!)
        }
    }


}